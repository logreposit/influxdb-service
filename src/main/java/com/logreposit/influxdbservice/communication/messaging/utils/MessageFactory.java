package com.logreposit.influxdbservice.communication.messaging.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory
{
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageFactory(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }
}
