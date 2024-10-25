package com.logreposit.influxdbservice.communication.messaging.rabbitmq;

import com.logreposit.influxdbservice.communication.messaging.common.MessageType;
import com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.retry.BackoffRetryStrategy;
import com.logreposit.influxdbservice.configuration.RabbitConfiguration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqAutoConfigurationCommandLineRunner implements CommandLineRunner {
  private static final Logger logger =
      LoggerFactory.getLogger(RabbitMqAutoConfigurationCommandLineRunner.class);

  private static final String ERROR_EXCHANGE_NAME = "error.x";

  private static final List<MessageType> SUBSCRIBED_MESSAGE_TYPES =
      Arrays.asList(
          MessageType.EVENT_USER_CREATED,
          MessageType.EVENT_DEVICE_CREATED,
          MessageType.EVENT_GENERIC_LOGDATA_RECEIVED);

  private final RabbitConfiguration rabbitConfiguration;
  private final AmqpAdmin amqpAdmin;

  public RabbitMqAutoConfigurationCommandLineRunner(
      RabbitConfiguration rabbitConfiguration, AmqpAdmin amqpAdmin) {
    this.rabbitConfiguration = rabbitConfiguration;
    this.amqpAdmin = amqpAdmin;
  }

  @Override
  public void run(String... args) {
    this.configureRabbit();
  }

  private void configureRabbit() {
    this.declareErrorExchangeAndQueue();
    this.declareRetryExchangesQueuesAndBindings();

    this.declareExchanges();
    this.declareBindings();
  }

  private void declareExchanges() {
    for (MessageType messageType : MessageType.values()) {
      String exchangeName = String.format("x.%s", messageType.toString().toLowerCase());

      this.declareFanoutExchange(exchangeName);
    }
  }

  private void declareRetryExchangesQueuesAndBindings() {
    for (int retryInterval : BackoffRetryStrategy.RETRY_INTERVALS) {
      String retryExchangeName =
          BackoffRetryStrategy.getExchangeNameForRetryInterval(retryInterval);
      Exchange retryExchange = this.declareFanoutExchange(retryExchangeName);

      Queue retryQueue =
          QueueBuilder.durable("retry.q." + retryInterval)
              .withArgument("x-dead-letter-exchange", "")
              .withArgument("x-message-ttl", retryInterval)
              .build();

      this.amqpAdmin.declareQueue(retryQueue);

      Binding binding = BindingBuilder.bind(retryQueue).to(retryExchange).with("").noargs();

      this.amqpAdmin.declareBinding(binding);
    }
  }

  private void declareErrorExchangeAndQueue() {
    Exchange errorExchange =
        ExchangeBuilder.directExchange(ERROR_EXCHANGE_NAME).durable(true).build();

    this.amqpAdmin.declareExchange(errorExchange);

    Queue errorQueue = QueueBuilder.durable("error." + this.rabbitConfiguration.getQueue()).build();

    this.amqpAdmin.declareQueue(errorQueue);
  }

  private Exchange declareFanoutExchange(String exchangeName) {
    Exchange exchange = ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();

    logger.warn("Declaring exchange '{}' ...", exchangeName);

    this.amqpAdmin.declareExchange(exchange);

    logger.warn("Declared exchange '{}'.", exchangeName);

    return exchange;
  }

  private void declareBindings() {
    String queueName = this.rabbitConfiguration.getQueue();

    for (MessageType messageType : SUBSCRIBED_MESSAGE_TYPES) {
      String exchangeName = String.format("x.%s", messageType.toString().toLowerCase());

      this.declareBinding(queueName, exchangeName, "");
    }

    this.declareBinding("error." + queueName, ERROR_EXCHANGE_NAME, queueName);
  }

  private void declareBinding(String queueName, String exchangeName, String routingKey) {
    Binding binding =
        new Binding(
            queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, new HashMap<>());

    logger.info("Declaring binding {} == ({}) ==> {} ...", exchangeName, routingKey, queueName);

    this.amqpAdmin.declareBinding(binding);

    logger.info("Declared binding {} => {}.", exchangeName, queueName);
  }
}
