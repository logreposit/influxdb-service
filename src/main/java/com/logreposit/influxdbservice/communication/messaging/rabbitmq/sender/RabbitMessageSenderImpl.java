package com.logreposit.influxdbservice.communication.messaging.rabbitmq.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessageSenderException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMessageSenderImpl implements RabbitMessageSender
{
    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageSenderImpl.class);

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMessageSenderImpl(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate)
    {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Message message) throws MessageSenderException
    {
        String exchange = String.format("x.%s", message.getType().toLowerCase());
        String routingKey = UUID.randomUUID().toString();
        String payload = this.serializeMessageForAMQPDelivery(message);

        logger.info("Sending message to exchange '{}' with routing key '{}': {}", exchange, routingKey, LoggingUtils.serializeForLoggingWithDefault(message));

        this.rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }

    @Override
    public void send(String exchange, String routingKey, org.springframework.amqp.core.Message message, Map<String, Object> headers)
    {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message, m -> {
            m.getMessageProperties().getHeaders().putAll(headers);
            return m;
        });
    }

    private String serializeMessageForAMQPDelivery(Message message) throws MessageSenderException
    {
        try
        {
            String serialized = this.objectMapper.writeValueAsString(message);

            return serialized;
        }
        catch (JsonProcessingException exception)
        {
            logger.error("Unable to serialize Message instance: {}", LoggingUtils.getLogForException(exception));
            throw new MessageSenderException("Unable to serialize Message instance", exception);
        }
    }
}
