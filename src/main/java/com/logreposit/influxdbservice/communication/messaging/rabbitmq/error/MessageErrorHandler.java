package com.logreposit.influxdbservice.communication.messaging.rabbitmq.error;

import org.springframework.amqp.core.Message;

public interface MessageErrorHandler
{
    void handleError(Message amqpMessage, Exception exception);
}
