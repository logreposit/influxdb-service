package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;

@Data
public class TagDto {
  private String name;
  private String value;
}
