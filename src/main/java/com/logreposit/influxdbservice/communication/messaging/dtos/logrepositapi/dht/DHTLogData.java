package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.dht;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DHTLogData
{
    private Date date;
    private String location;
    private Double humidity;
    private Double temperature;
}
