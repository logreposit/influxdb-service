package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CmiLogData
{
    private Date                      date;
    private DeviceType                deviceType;
    private List<Input>               inputs;
    private List<Output>              outputs;
    private List<AnalogLoggingValue>  analogLoggingValues;
    private List<DigitalLoggingValue> digitalLoggingValues;

    public CmiLogData()
    {
        this.inputs               = new ArrayList<>();
        this.outputs              = new ArrayList<>();
        this.analogLoggingValues  = new ArrayList<>();
        this.digitalLoggingValues = new ArrayList<>();
    }
}
