package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200.FroelingS3200LogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.s3200.FroelingLambdatronicS3200LogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.s3200.FroelingLambdatronicS3200LogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventFroelingLambdatronicS3200LogdataReceivedMessageProcessor extends AbstractMessageProcessor<FroelingS3200LogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventFroelingLambdatronicS3200LogdataReceivedMessageProcessor.class);

    private final InfluxDBService                                    influxDBService;
    private final FroelingLambdatronicS3200LogdataBatchPointsFactory froelingLambdatronicS3200LogdataBatchPointsFactory;

    @Autowired
    public EventFroelingLambdatronicS3200LogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                                         InfluxDBService influxDBService,
                                                                         FroelingLambdatronicS3200LogdataBatchPointsFactory froelingLambdatronicS3200LogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService                                    = influxDBService;
        this.froelingLambdatronicS3200LogdataBatchPointsFactory = froelingLambdatronicS3200LogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String               userId               = message.getMetaData().getUserId();
        String               deviceId             = message.getMetaData().getDeviceId();
        FroelingS3200LogData froelingS3200LogData = this.getMessagePayload(message, FroelingS3200LogData.class);

        logger.info(
                "Retrieved FroelingS3200LogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(froelingS3200LogData)
        );

        try
        {
            BatchPoints batchPoints = this.froelingLambdatronicS3200LogdataBatchPointsFactory.createBatchPoints(deviceId, froelingS3200LogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (FroelingLambdatronicS3200LogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught FroelingLambdatronicS3200LogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught FroelingLambdatronicS3200LogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
