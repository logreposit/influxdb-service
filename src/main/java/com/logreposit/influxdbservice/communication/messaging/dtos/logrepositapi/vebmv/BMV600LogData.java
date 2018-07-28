package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BMV600LogData
{
    private Date    date;
    private Boolean alarm;
    private Boolean relay;
    private Double  stateOfCharge;
    private Integer batteryVoltage;
    private Integer starterBatteryVoltage;
    private Integer current;
    private Integer consumedEnergy;
    private Integer timeToGo;

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

    public Double getStateOfCharge()
    {
        return this.stateOfCharge;
    }

    public void setStateOfCharge(Double stateOfCharge)
    {
        this.stateOfCharge = stateOfCharge;
    }

    public Integer getBatteryVoltage()
    {
        return this.batteryVoltage;
    }

    public void setBatteryVoltage(Integer batteryVoltage)
    {
        this.batteryVoltage = batteryVoltage;
    }

    public Integer getStarterBatteryVoltage()
    {
        return this.starterBatteryVoltage;
    }

    public void setStarterBatteryVoltage(Integer starterBatteryVoltage)
    {
        this.starterBatteryVoltage = starterBatteryVoltage;
    }

    public Integer getCurrent()
    {
        return this.current;
    }

    public void setCurrent(Integer current)
    {
        this.current = current;
    }

    public Integer getConsumedEnergy()
    {
        return this.consumedEnergy;
    }

    public void setConsumedEnergy(Integer consumedEnergy)
    {
        this.consumedEnergy = consumedEnergy;
    }

    public Integer getTimeToGo()
    {
        return this.timeToGo;
    }

    public void setTimeToGo(Integer timeToGo)
    {
        this.timeToGo = timeToGo;
    }
}
