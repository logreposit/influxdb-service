package com.logreposit.influxdbservice.communication.messaging.handler.processors;

import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;

public class MessageProcessorException extends RetryableMessagingException
{
    public MessageProcessorException(String message)
    {
        super(message);
    }

    public MessageProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
