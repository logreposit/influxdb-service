package com.logreposit.influxdbservice.communication.messaging.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DeviceCreatedMessageDto {
  private String id;
  private String name;
}
