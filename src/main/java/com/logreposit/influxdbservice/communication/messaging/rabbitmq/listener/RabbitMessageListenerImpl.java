package com.logreposit.influxdbservice.communication.messaging.rabbitmq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.NotRetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.MessageHandler;
import com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.MessageErrorHandler;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class RabbitMessageListenerImpl implements RabbitMessageListener
{
    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageListenerImpl.class);

    private final ObjectMapper objectMapper;
    private final MessageHandler messageHandler;
    private final MessageErrorHandler messageErrorHandler;

    @Autowired
    public RabbitMessageListenerImpl(ObjectMapper objectMapper,
                                     MessageHandler messageHandler,
                                     MessageErrorHandler messageErrorHandler)
    {
        this.objectMapper = objectMapper;
        this.messageHandler = messageHandler;
        this.messageErrorHandler = messageErrorHandler;
    }

    @Override
    @RabbitListener(queues = "${influxdbservice.communication.messaging.rabbit.queue}")
    public void listen(org.springframework.amqp.core.Message amqpMessage)
    {
        try
        {
            if (amqpMessage.getMessageProperties().getRedelivered())
                logger.warn("Message is redelivered.");

            String payload = new String(amqpMessage.getBody(), StandardCharsets.UTF_8);
            Message bitmovinMessage = this.convertStringToBitmovinMessage(payload);

            this.messageHandler.handleMessage(bitmovinMessage);
        }
        catch (Exception exception)
        {
            logger.error("Caught Exception while processing AMQP message: {}", LoggingUtils.getLogForException(exception));
            this.messageErrorHandler.handleError(amqpMessage, exception);
        }
    }

    private Message convertStringToBitmovinMessage(String payload) throws NotRetryableMessagingException
    {
        try
        {
            Message message = this.objectMapper.readValue(payload, Message.class);

            return message;
        }
        catch (IOException exception)
        {
            logger.error("Unable to deserialize message payload to bitmovin Message instance: {}", LoggingUtils.getLogForException(exception));
            throw new NotRetryableMessagingException("Unable to deserialize AMQP Message Payload to bitmovin Message instance", exception);
        }
    }
}
