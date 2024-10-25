package com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.dtos.UserCreatedMessageDto;
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
public class EventUserCreatedMessageProcessor
    extends AbstractMessageProcessor<UserCreatedMessageDto> {
  private static final Logger logger =
      LoggerFactory.getLogger(EventUserCreatedMessageProcessor.class);

  private final InfluxDBService influxDBService;

  @Autowired
  public EventUserCreatedMessageProcessor(
      ObjectMapper objectMapper, InfluxDBService influxDBService) {
    super(objectMapper);

    this.influxDBService = influxDBService;
  }

  @Override
  public void processMessage(Message message) throws MessagingException {
    UserCreatedMessageDto user = this.getMessagePayload(message, UserCreatedMessageDto.class);

    logger.info("Retrieved created User: {}", LoggingUtils.serialize(user));

    try {
      this.influxDBService.createUser(user.getEmail(), user.getPassword());

      logger.info("Successfully created user.");
    } catch (InfluxDBServiceException exception) {
      logger.error("Caught InfluxDBServiceException while creating user", exception);
      throw new RetryableMessagingException(
          "Caught InfluxDBServiceException while creating user", exception);
    }
  }
}
