package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BMV600LogData
{
    private Date    date;
    private Boolean alarm;
    private Boolean relay;
    private Double  voltage;
    private Double  current;
    private Double  stateOfCharge;

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Boolean getAlarm()
    {
        return this.alarm;
    }

    public void setAlarm(Boolean alarm)
    {
        this.alarm = alarm;
    }

    public Boolean getRelay()
    {
        return this.relay;
    }

    public void setRelay(Boolean relay)
    {
        this.relay = relay;
    }

    public Double getVoltage()
    {
        return this.voltage;
    }

    public void setVoltage(Double voltage)
    {
        this.voltage = voltage;
    }

    public Double getCurrent()
    {
        return this.current;
    }

    public void setCurrent(Double current)
    {
        this.current = current;
    }

    public Double getStateOfCharge()
    {
        return this.stateOfCharge;
    }

    public void setStateOfCharge(Double stateOfCharge)
    {
        this.stateOfCharge = stateOfCharge;
    }
}
