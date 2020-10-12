package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Output extends AbstractIO
{
    private Integer state;
}
