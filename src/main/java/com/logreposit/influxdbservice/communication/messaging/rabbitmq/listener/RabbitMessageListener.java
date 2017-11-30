package com.logreposit.influxdbservice.communication.messaging.rabbitmq.listener;

import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;

public interface RabbitMessageListener
{
    void listen(org.springframework.amqp.core.Message amqpMessage) throws MessagingException;
}
