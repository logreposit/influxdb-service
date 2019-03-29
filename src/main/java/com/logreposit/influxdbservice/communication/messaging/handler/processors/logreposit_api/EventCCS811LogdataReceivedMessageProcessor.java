package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.ccs811.CCS811LogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.ccs811.CCS811LogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.ccs811.CCS811LogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCCS811LogdataReceivedMessageProcessor extends AbstractMessageProcessor<CCS811LogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventCCS811LogdataReceivedMessageProcessor.class);

    private final InfluxDBService                 influxDBService;
    private final CCS811LogdataBatchPointsFactory ccs811LogdataBatchPointsFactory;

    @Autowired
    public EventCCS811LogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                      InfluxDBService influxDBService,
                                                      CCS811LogdataBatchPointsFactory ccs811LogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService                 = influxDBService;
        this.ccs811LogdataBatchPointsFactory = ccs811LogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String        userId        = message.getMetaData().getUserId();
        String        deviceId      = message.getMetaData().getDeviceId();
        CCS811LogData ccs811LogData = this.getMessagePayload(message, CCS811LogData.class);

        logger.info(
                "Retrieved CCS811LogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(ccs811LogData)
        );

        try
        {
            BatchPoints batchPoints = this.ccs811LogdataBatchPointsFactory.createBatchPoints(deviceId, ccs811LogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (CCS811LogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught CCS811LogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught CCS811LogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
