package com.logreposit.influxdbservice.communication.messaging.rabbitmq.listener;

public interface RabbitMessageListener
{
    void listen(org.springframework.amqp.core.Message amqpMessage);
}
