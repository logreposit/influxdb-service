package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.dht.DHTLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.dht.DHTLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.dht.DHTLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDHTLogdataReceivedMessageProcessor extends AbstractMessageProcessor<DHTLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventDHTLogdataReceivedMessageProcessor.class);

    private final InfluxDBService              influxDBService;
    private final DHTLogdataBatchPointsFactory dhtLogdataBatchPointsFactory;

    @Autowired
    public EventDHTLogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                   InfluxDBService influxDBService,
                                                   DHTLogdataBatchPointsFactory dhtLogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService              = influxDBService;
        this.dhtLogdataBatchPointsFactory = dhtLogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String     userId     = message.getMetaData().getUserId();
        String     deviceId   = message.getMetaData().getDeviceId();
        DHTLogData dhtLogData = this.getMessagePayload(message, DHTLogData.class);

        logger.info(
                "Retrieved DHTLogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(dhtLogData)
        );

        try
        {
            BatchPoints batchPoints = this.dhtLogdataBatchPointsFactory.createBatchPoints(deviceId, dhtLogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (DHTLogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught DHTLogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught DHTLogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
