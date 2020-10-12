package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.enums.SignalType;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.enums.Unit;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public abstract class AbstractIO
{
    private Integer    number;
    private SignalType signal;
    private Unit       unit;
    private Double     value;
}
