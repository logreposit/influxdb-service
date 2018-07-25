package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.CmiLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.tacmi.CmiLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.tacmi.CmiLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCmiLogdataReceivedMessageProcessor extends AbstractMessageProcessor<CmiLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventCmiLogdataReceivedMessageProcessor.class);

    private final InfluxDBService              influxDBService;
    private final CmiLogdataBatchPointsFactory cmiLogdataBatchPointsFactory;

    @Autowired
    public EventCmiLogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                   InfluxDBService influxDBService,
                                                   CmiLogdataBatchPointsFactory cmiLogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService              = influxDBService;
        this.cmiLogdataBatchPointsFactory = cmiLogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String     userId         = message.getMetaData().getUserId();
        String     deviceId       = message.getMetaData().getDeviceId();
        CmiLogData cmiLogData     = this.getMessagePayload(message, CmiLogData.class);

        logger.info(
                "Retrieved CmiLogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(cmiLogData)
        );

        try
        {
            BatchPoints batchPoints = this.cmiLogdataBatchPointsFactory.createBatchPoints(deviceId, cmiLogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (CmiLogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught CmiLogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught CmiLogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
