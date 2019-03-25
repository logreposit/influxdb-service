package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.cotek;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CotekSPSeriesLogData
{
    private Date    date;
    private Integer outputFrequency;
    private Integer outputVoltage;
    private Integer outputCurrent;
    private Integer outputPower;
    private Integer inputVoltage;

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getOutputFrequency()
    {
        return this.outputFrequency;
    }

    public void setOutputFrequency(Integer outputFrequency)
    {
        this.outputFrequency = outputFrequency;
    }

    public Integer getOutputVoltage()
    {
        return this.outputVoltage;
    }

    public void setOutputVoltage(Integer outputVoltage)
    {
        this.outputVoltage = outputVoltage;
    }

    public Integer getOutputCurrent()
    {
        return this.outputCurrent;
    }

    public void setOutputCurrent(Integer outputCurrent)
    {
        this.outputCurrent = outputCurrent;
    }

    public Integer getOutputPower()
    {
        return this.outputPower;
    }

    public void setOutputPower(Integer outputPower)
    {
        this.outputPower = outputPower;
    }

    public Integer getInputVoltage()
    {
        return this.inputVoltage;
    }

    public void setInputVoltage(Integer inputVoltage)
    {
        this.inputVoltage = inputVoltage;
    }
}
