package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.sds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class SolarLogLogData
{
    private Date    date;
    private Integer powerAc;
    private Integer powerDc;
    private Integer voltageAc;
    private Integer voltageDc;
    private Integer yieldDay;
    private Integer yieldYesterday;
    private Integer yieldMonth;
    private Integer yieldYear;
    private Integer yieldTotal;
    private Integer consumptionPower;
    private Integer consumptionYieldDay;
    private Integer consumptionYieldYesterday;
    private Integer consumptionYieldMonth;
    private Integer consumptionYieldYear;
    private Integer consumptionYieldTotal;
    private Integer totalPower;
}
