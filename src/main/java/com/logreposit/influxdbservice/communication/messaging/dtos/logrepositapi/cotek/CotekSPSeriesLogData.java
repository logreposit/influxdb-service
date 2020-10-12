package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.cotek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CotekSPSeriesLogData
{
    private Date    date;
    private Integer outputFrequency;
    private Integer outputVoltage;
    private Integer outputCurrent;
    private Integer outputPower;
    private Integer inputVoltage;
}
