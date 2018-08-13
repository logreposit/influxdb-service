package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.rtlsdr.LacrosseTXLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.rtlsdr.LacrosseTXLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.rtlsdr.LacrosseTXLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventLacrosseTXLogdataReceivedMessageProcessor extends AbstractMessageProcessor<LacrosseTXLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventLacrosseTXLogdataReceivedMessageProcessor.class);

    private final InfluxDBService                     influxDBService;
    private final LacrosseTXLogdataBatchPointsFactory lacrosseTXLogdataBatchPointsFactory;

    @Autowired
    public EventLacrosseTXLogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                          InfluxDBService influxDBService,
                                                          LacrosseTXLogdataBatchPointsFactory lacrosseTXLogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService = influxDBService;
        this.lacrosseTXLogdataBatchPointsFactory = lacrosseTXLogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String            userId            = message.getMetaData().getUserId();
        String            deviceId          = message.getMetaData().getDeviceId();
        LacrosseTXLogData lacrosseTXLogData = this.getMessagePayload(message, LacrosseTXLogData.class);

        logger.info(
                "Retrieved LacrosseTXLogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(lacrosseTXLogData)
        );

        try
        {
            BatchPoints batchPoints = this.lacrosseTXLogdataBatchPointsFactory.createBatchPoints(deviceId, lacrosseTXLogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (LacrosseTXLogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught LacrosseTXLogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught LacrosseTXLogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
