package com.logreposit.influxdbservice.communication.messaging.rabbitmq;

import com.logreposit.influxdbservice.communication.messaging.common.MessageType;
import com.logreposit.influxdbservice.configuration.RabbitConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RabbitMqAutoConfigurationCommandLineRunner implements CommandLineRunner
{
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqAutoConfigurationCommandLineRunner.class);

    private static final List<MessageType> SUBSCRIBED_MESSAGE_TYPES =
            Arrays.asList(MessageType.EVENT_USER_CREATED, MessageType.EVENT_DEVICE_CREATED, MessageType.EVENT_CMI_LOGDATA_RECEIVED);

    private final RabbitConfiguration rabbitConfiguration;
    private final AmqpAdmin           amqpAdmin;

    public RabbitMqAutoConfigurationCommandLineRunner(RabbitConfiguration rabbitConfiguration, AmqpAdmin amqpAdmin)
    {
        this.rabbitConfiguration = rabbitConfiguration;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void run(String... args)
    {
        this.configureRabbit();
    }

    private void configureRabbit()
    {
        this.declareExchanges();
        this.declareQueue();
        this.declareBindings();
    }

    private void declareExchanges()
    {
        for (MessageType messageType : MessageType.values())
        {
            String   exchangeName = String.format("x.%s", messageType.toString().toLowerCase());
            Exchange exchange     = ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();

            logger.warn("Declaring exchange '{}' for MessageType '{}' ...", exchangeName, messageType);

            this.amqpAdmin.declareExchange(exchange);

            logger.warn("Declared exchange '{}'.", exchangeName);
        }
    }

    private void declareQueue()
    {
        String queueName = this.rabbitConfiguration.getQueue();
        Queue  queue     = new Queue(queueName, true);

        logger.warn("declaring queue '{}' ...", queueName);

        this.amqpAdmin.declareQueue(queue);

        logger.warn("declared queue '{}'.", queueName);
    }

    private void declareBindings()
    {
        String queueName = this.rabbitConfiguration.getQueue();

        for (MessageType messageType : SUBSCRIBED_MESSAGE_TYPES)
        {
            String  exchangeName = String.format("x.%s", messageType.toString().toLowerCase());
            Binding binding      = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, null, null);

            logger.info("Declaring binding {} => {} ...", exchangeName, queueName);

            this.amqpAdmin.declareBinding(binding);

            logger.info("Declared binding {} => {}.", exchangeName, queueName);
        }
    }
}
