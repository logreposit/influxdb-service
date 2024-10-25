package com.logreposit.influxdbservice.communication.messaging.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Message {
  private String id;
  private Date date;
  private String type;
  private MessageMetaData metaData;
  private String payload;
}
