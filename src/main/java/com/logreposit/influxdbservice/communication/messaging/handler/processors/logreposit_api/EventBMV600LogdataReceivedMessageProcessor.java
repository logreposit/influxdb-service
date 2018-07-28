package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv.BMV600LogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.vebmv.BMV600LogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.vebmv.BMV600LogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventBMV600LogdataReceivedMessageProcessor extends AbstractMessageProcessor<BMV600LogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventBMV600LogdataReceivedMessageProcessor.class);

    private final InfluxDBService                 influxDBService;
    private final BMV600LogdataBatchPointsFactory bmv600LogdataBatchPointsFactory;

    @Autowired
    public EventBMV600LogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                      InfluxDBService influxDBService,
                                                      BMV600LogdataBatchPointsFactory bmv600LogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService                 = influxDBService;
        this.bmv600LogdataBatchPointsFactory = bmv600LogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String        userId        = message.getMetaData().getUserId();
        String        deviceId      = message.getMetaData().getDeviceId();
        BMV600LogData bmv600LogData = this.getMessagePayload(message, BMV600LogData.class);

        logger.info(
                "Retrieved BMV600LogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(bmv600LogData)
        );

        try
        {
            BatchPoints batchPoints = this.bmv600LogdataBatchPointsFactory.createBatchPoints(deviceId, bmv600LogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (BMV600LogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught BMV600LogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught BMV600LogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
