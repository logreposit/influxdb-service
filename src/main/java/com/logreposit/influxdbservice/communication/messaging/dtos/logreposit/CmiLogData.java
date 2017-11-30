package com.logreposit.influxdbservice.communication.messaging.dtos.logreposit;

/*
 * Created by dom on 10/21/17
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.enums.DeviceType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CmiLogData
{
    private Date date;
    private DeviceType deviceType;
    private List<Input> inputs;
    private List<Output> outputs;
    private List<AnalogLoggingValue> analogLoggingValues;
    private List<DigitalLoggingValue> digitalLoggingValues;

    public CmiLogData()
    {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.analogLoggingValues = new ArrayList<>();
        this.digitalLoggingValues = new ArrayList<>();
    }

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public DeviceType getDeviceType()
    {
        return this.deviceType;
    }

    public void setDeviceType(DeviceType deviceType)
    {
        this.deviceType = deviceType;
    }

    public List<Input> getInputs()
    {
        return this.inputs;
    }

    public void setInputs(List<Input> inputs)
    {
        this.inputs = inputs;
    }

    public List<Output> getOutputs()
    {
        return this.outputs;
    }

    public void setOutputs(List<Output> outputs)
    {
        this.outputs = outputs;
    }

    public List<AnalogLoggingValue> getAnalogLoggingValues()
    {
        return this.analogLoggingValues;
    }

    public void setAnalogLoggingValues(List<AnalogLoggingValue> analogLoggingValues)
    {
        this.analogLoggingValues = analogLoggingValues;
    }

    public List<DigitalLoggingValue> getDigitalLoggingValues()
    {
        return this.digitalLoggingValues;
    }

    public void setDigitalLoggingValues(List<DigitalLoggingValue> digitalLoggingValues)
    {
        this.digitalLoggingValues = digitalLoggingValues;
    }
}
