package com.logreposit.influxdbservice.communication.messaging.handler;

import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;

public interface MessageHandler
{
    void handleMessage(Message message) throws MessagingException;
}
