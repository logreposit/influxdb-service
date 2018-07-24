package com.logreposit.influxdbservice.communication.messaging.dtos.logreposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.enums.SignalType;
import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.enums.Unit;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractIO
{
    private Integer    number;
    private SignalType signal;
    private Unit       unit;
    private Double     value;

    public Integer getNumber()
    {
        return this.number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public SignalType getSignal()
    {
        return this.signal;
    }

    public void setSignal(SignalType signal)
    {
        this.signal = signal;
    }

    public Unit getUnit()
    {
        return this.unit;
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }

    public Double getValue()
    {
        return this.value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }
}
