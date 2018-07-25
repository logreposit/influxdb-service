package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.enums.RasState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Input extends AbstractIO
{
    private RasState rasState;

    public RasState getRasState()
    {
        return this.rasState;
    }

    public void setRasState(RasState rasState)
    {
        this.rasState = rasState;
    }
}
