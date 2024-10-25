package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.common.MessageMetaData;
import com.logreposit.influxdbservice.communication.messaging.dtos.DeviceCreatedMessageDto;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.NotRetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBServiceException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDeviceCreatedMessageProcessor extends AbstractMessageProcessor<DeviceCreatedMessageDto>
{
    private static final Logger logger = LoggerFactory.getLogger(EventDeviceCreatedMessageProcessor.class);

    private final InfluxDBService influxDBService;

    @Autowired
    public EventDeviceCreatedMessageProcessor(ObjectMapper objectMapper, InfluxDBService influxDBService)
    {
        super(objectMapper);

        this.influxDBService = influxDBService;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        this.validateMessage(message);

        DeviceCreatedMessageDto device = this.getMessagePayload(message, DeviceCreatedMessageDto.class);

        logger.info("Retrieved created Device: {}", LoggingUtils.serialize(device));

        try
        {
            this.influxDBService.createDatabase(device.getId(), message.getMetaData().getUserEmail());

            logger.info("Successfully created database and configured permissions.");
        }
        catch (InfluxDBServiceException exception)
        {
            logger.error("Caught InfluxDBServiceException while creating database", exception);
            throw new RetryableMessagingException("Caught InfluxDBServiceException while creating database", exception);
        }
    }

    private void validateMessage(Message message) throws NotRetryableMessagingException
    {
        if (message == null || message.getMetaData() == null)
        {
            logger.error("Message MetaData missing.");
            throw new NotRetryableMessagingException("Message MetaData missing.");
        }

        MessageMetaData messageMetaData = message.getMetaData();

        if (StringUtils.isEmpty(messageMetaData.getUserEmail()))
        {
            logger.error("metaData.userEmail missing");
            throw new NotRetryableMessagingException("metaData.userEmail is missing.");
        }
    }
}
