package com.logreposit.influxdbservice.communication.messaging.dtos.logreposit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Output extends AbstractIO
{
    private Integer state;

    public Integer getState()
    {
        return this.state;
    }

    public void setState(Integer state)
    {
        this.state = state;
    }
}
