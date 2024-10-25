package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.ReadingDto;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.AbstractMessageProcessor;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBService;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.generic.GenericLogdataBatchPointsFactory;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.generic.GenericLogdataBatchPointsFactoryException;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import java.util.List;
import org.influxdb.dto.BatchPoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventGenericLogdataReceivedMessageProcessor
    extends AbstractMessageProcessor<List<ReadingDto>> {
  private static final Logger logger =
      LoggerFactory.getLogger(EventGenericLogdataReceivedMessageProcessor.class);

  private final InfluxDBService influxDBService;
  private final GenericLogdataBatchPointsFactory genericLogdataBatchPointsFactory;

  @Autowired
  public EventGenericLogdataReceivedMessageProcessor(
      ObjectMapper objectMapper,
      InfluxDBService influxDBService,
      GenericLogdataBatchPointsFactory genericLogdataBatchPointsFactory) {
    super(objectMapper);

    this.influxDBService = influxDBService;
    this.genericLogdataBatchPointsFactory = genericLogdataBatchPointsFactory;
  }

  @Override
  public void processMessage(Message message) throws MessagingException {
    String userId = message.getMetaData().getUserId();
    String deviceId = message.getMetaData().getDeviceId();
    List<ReadingDto> logData =
        this.getMessagePayload(message, new TypeReference<List<ReadingDto>>() {});

    logger.info(
        "Retrieved List<ReadingDto> for Device '{}' of User '{}': {}",
        deviceId,
        userId,
        LoggingUtils.serialize(logData));

    try {
      BatchPoints batchPoints =
          this.genericLogdataBatchPointsFactory.createBatchPoints(deviceId, logData);

      this.influxDBService.insert(batchPoints);

      logger.info("Successfully processed Payload.");
    } catch (GenericLogdataBatchPointsFactoryException exception) {
      logger.error(
          "Caught GenericLogdataBatchPointsFactoryException while preparing data for insertion into DB",
          exception);
      throw new RetryableMessagingException(
          "Caught GenericLogdataBatchPointsFactoryException while preparing data for insertion into DB",
          exception);
    }
  }
}
