package com.logreposit.influxdbservice.communication.messaging.dtos.logreposit;

/*
 * Created by dom on 10/21/17
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.enums.RasState;

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
