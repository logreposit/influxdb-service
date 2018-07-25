package com.logreposit.influxdbservice.communication.messaging.handler.processors;

import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;

public interface MessageProcessor
{
    void processMessage(Message message) throws MessagingException;
}
