package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.ccs811;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CCS811LogData
{
    private Date date;
    private Integer eco2;
    private Integer tvoc;
    private Double temperature;
}
