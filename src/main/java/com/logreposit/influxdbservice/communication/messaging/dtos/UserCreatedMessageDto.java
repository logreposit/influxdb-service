package com.logreposit.influxdbservice.communication.messaging.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserCreatedMessageDto {
  private String id;
  private String email;
  private String password;
  private List<String> roles;
}
