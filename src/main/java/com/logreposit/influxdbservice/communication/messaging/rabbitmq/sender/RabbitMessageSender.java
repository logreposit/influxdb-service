package com.logreposit.influxdbservice.communication.messaging.rabbitmq.sender;

import com.logreposit.influxdbservice.communication.messaging.sender.MessageSender;

import java.util.Map;

public interface RabbitMessageSender extends MessageSender
{
    void send(String exchange, String routingKey, org.springframework.amqp.core.Message message, Map<String, Object> headers);
}
