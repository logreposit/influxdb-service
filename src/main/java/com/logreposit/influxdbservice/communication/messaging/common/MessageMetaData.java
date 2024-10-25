package com.logreposit.influxdbservice.communication.messaging.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class MessageMetaData {
  private String userId;
  private String userEmail;
  private String deviceId;
  private String correlationId;
}
