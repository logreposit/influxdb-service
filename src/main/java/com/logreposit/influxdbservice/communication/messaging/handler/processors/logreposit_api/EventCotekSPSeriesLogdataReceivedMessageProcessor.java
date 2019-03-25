package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.cotek.CotekSPSeriesLogData;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.cotek.CotekSPSeriesLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.cotek.CotekSPSeriesLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCotekSPSeriesLogdataReceivedMessageProcessor extends AbstractMessageProcessor<CotekSPSeriesLogData>
{
    private static final Logger logger = LoggerFactory.getLogger(EventCotekSPSeriesLogdataReceivedMessageProcessor.class);

    private final InfluxDBService                        influxDBService;
    private final CotekSPSeriesLogdataBatchPointsFactory cotekSPSeriesLogdataBatchPointsFactory;

    @Autowired
    public EventCotekSPSeriesLogdataReceivedMessageProcessor(ObjectMapper objectMapper,
                                                             InfluxDBService influxDBService,
                                                             CotekSPSeriesLogdataBatchPointsFactory cotekSPSeriesLogdataBatchPointsFactory)
    {
        super(objectMapper);

        this.influxDBService                        = influxDBService;
        this.cotekSPSeriesLogdataBatchPointsFactory = cotekSPSeriesLogdataBatchPointsFactory;
    }

    @Override
    public void processMessage(Message message) throws MessagingException
    {
        String               userId               = message.getMetaData().getUserId();
        String               deviceId             = message.getMetaData().getDeviceId();
        CotekSPSeriesLogData cotekSPSeriesLogData = this.getMessagePayload(message, CotekSPSeriesLogData.class);

        logger.info(
                "Retrieved CotekSPSeriesLogData for Device '{}' of User '{}': {}",
                deviceId,
                userId,
                LoggingUtils.serialize(cotekSPSeriesLogData)
        );

        try
        {
            BatchPoints batchPoints = this.cotekSPSeriesLogdataBatchPointsFactory.createBatchPoints(deviceId, cotekSPSeriesLogData);

            this.influxDBService.insert(batchPoints);

            logger.info("Successfully processed Payload.");
        }
        catch (CotekSPSeriesLogdataBatchPointsFactoryException exception)
        {
            logger.error("Caught CotekSPSeriesLogdataBatchPointsFactoryException while preparing data for insertion into DB: {}", LoggingUtils.getLogForException(exception));
            throw new RetryableMessagingException("Caught CotekSPSeriesLogdataBatchPointsFactoryException while preparing data for insertion into DB", exception);
        }
    }
}
