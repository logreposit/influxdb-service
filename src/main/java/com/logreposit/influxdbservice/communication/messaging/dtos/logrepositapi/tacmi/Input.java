package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.enums.RasState;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Input extends AbstractIO
{
    private RasState rasState;
}
