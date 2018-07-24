package com.logreposit.influxdbservice.communication.messaging.handler.processors.cmi_reader_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.CmiLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBServiceException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCmiLogdataReceivedMessageProcessor extends AbstractMessageProcessor<CmiLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventCmiLogdataReceivedMessageProcessor.class);

    private final InfluxDBService influxDBService;

    @Autowired
    public EventCmiLogdataReceivedMessageProcessor(ObjectMapper objectMapper, InfluxDBService influxDBService)
    {
        super(objectMapper);

        this.influxDBService = influxDBService;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String organizationId = message.getMetaData().getOrganizationId();
        String deviceId = message.getMetaData().getDeviceId();
        CmiLogData cmiLogData = this.getMessagePayload(message, CmiLogData.class);

        logger.info("Retrieved CmiLogData for Organization '{}' and Device '{}': {}",
                organizationId,
                deviceId,
                LoggingUtils.serialize(cmiLogData)
        );

        try
        {
            this.influxDBService.insert(organizationId, deviceId, cmiLogData);
            logger.info("Successfully processed Payload.");
        }
        catch (InfluxDBServiceException exception)
        {
            logger.error("Caught InfluxDBServiceException while inserting data: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught InfluxDBServiceException while inserting data", exception);
        }
    }
}
