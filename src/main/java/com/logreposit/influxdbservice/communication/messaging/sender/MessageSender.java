package com.logreposit.influxdbservice.communication.messaging.sender;

import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessageSenderException;

public interface MessageSender
{
    void send(Message message) throws MessageSenderException;
}
