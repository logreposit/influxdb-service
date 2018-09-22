package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.sds.SolarLogLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.sds.SolarLogLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.sds.SolarLogLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventSolarLogLogdataReceivedMessageProcessor extends AbstractMessageProcessor<SolarLogLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventSolarLogLogdataReceivedMessageProcessor.class);

    private final InfluxDBService                   influxDBService;
    private final SolarLogLogdataBatchPointsFactory solarLogLogdataBatchPointsFactory;

    @Autowired
    public EventSolarLogLogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                        InfluxDBService influxDBService,
                                                        SolarLogLogdataBatchPointsFactory solarLogLogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService                   = influxDBService;
        this.solarLogLogdataBatchPointsFactory = solarLogLogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String          userId          = message.getMetaData().getUserId();
        String          deviceId        = message.getMetaData().getDeviceId();
        SolarLogLogData solarlogLogData = this.getMessagePayload(message, SolarLogLogData.class);

        logger.info(
                "Retrieved SolarLogLogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(solarlogLogData)
        );

        try
        {
            BatchPoints batchPoints = this.solarLogLogdataBatchPointsFactory.createBatchPoints(deviceId, solarlogLogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (SolarLogLogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught SolarLogLogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught SolarLogLogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
