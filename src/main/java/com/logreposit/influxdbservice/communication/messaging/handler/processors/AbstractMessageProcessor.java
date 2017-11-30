package com.logreposit.influxdbservice.communication.messaging.handler.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.NotRetryableMessagingException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public abstract class AbstractMessageProcessor<T> implements MessageProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(AbstractMessageProcessor.class);

    protected ObjectMapper objectMapper;

    public AbstractMessageProcessor(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    protected T getMessagePayload(Message message, Class<T> clazz) throws NotRetryableMessagingException
    {
        try
        {
            T payload = this.objectMapper.reader().forType(clazz).readValue(message.getPayload());

            logger.info("Successfully deserialized Message Payload into {} instance: {}", clazz.toString(), LoggingUtils.serializeForLoggingWithDefault(payload));

            return payload;
        }
        catch (IOException exception)
        {
            logger.error("Unable to deserialize Message payload to instance of '{}'.", clazz.toString());
            throw new NotRetryableMessagingException(String.format("Unable to deserialize Message payload to instance of '%s'", clazz.toString()), exception);
        }
    }
}
