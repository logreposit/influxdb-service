package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.rtlsdr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LacrosseTXLogData
{
    private Date    date;
    private String  location;
    private Integer sensorId;
    private Boolean batteryNew;
    private Boolean batteryLow;
    private Double  temperature;
    private Double  humidity;

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Integer getSensorId()
    {
        return this.sensorId;
    }

    public void setSensorId(Integer sensorId)
    {
        this.sensorId = sensorId;
    }

    public Boolean getBatteryNew()
    {
        return this.batteryNew;
    }

    public void setBatteryNew(Boolean batteryNew)
    {
        this.batteryNew = batteryNew;
    }

    public Boolean getBatteryLow()
    {
        return this.batteryLow;
    }

    public void setBatteryLow(Boolean batteryLow)
    {
        this.batteryLow = batteryLow;
    }

    public Double getTemperature()
    {
        return this.temperature;
    }

    public void setTemperature(Double temperature)
    {
        this.temperature = temperature;
    }

    public Double getHumidity()
    {
        return this.humidity;
    }

    public void setHumidity(Double humidity)
    {
        this.humidity = humidity;
    }
}
