package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.ccs811;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CCS811LogData
{
    private Date date;
    private Integer eco2;
    private Integer tvoc;

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getEco2()
    {
        return this.eco2;
    }

    public void setEco2(Integer eco2)
    {
        this.eco2 = eco2;
    }

    public Integer getTvoc()
    {
        return this.tvoc;
    }

    public void setTvoc(Integer tvoc)
    {
        this.tvoc = tvoc;
    }
}
