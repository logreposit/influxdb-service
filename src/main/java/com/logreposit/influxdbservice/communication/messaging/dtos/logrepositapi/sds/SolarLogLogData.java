package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.sds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolarLogLogData
{
    private Date    date;
    private Integer powerAc;
    private Integer powerDc;
    private Integer voltageAc;
    private Integer voltageDc;
    private Integer yieldDay;
    private Integer yieldYesterday;
    private Integer yieldMonth;
    private Integer yieldYear;
    private Integer yieldTotal;
    private Integer consumptionPower;
    private Integer consumptionYieldDay;
    private Integer consumptionYieldYesterday;
    private Integer consumptionYieldMonth;
    private Integer consumptionYieldYear;
    private Integer consumptionYieldTotal;
    private Integer totalPower;

    public Date getDate()
    {
        return this.date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getPowerAc()
    {
        return this.powerAc;
    }

    public void setPowerAc(Integer powerAc)
    {
        this.powerAc = powerAc;
    }

    public Integer getPowerDc()
    {
        return this.powerDc;
    }

    public void setPowerDc(Integer powerDc)
    {
        this.powerDc = powerDc;
    }

    public Integer getVoltageAc()
    {
        return this.voltageAc;
    }

    public void setVoltageAc(Integer voltageAc)
    {
        this.voltageAc = voltageAc;
    }

    public Integer getVoltageDc()
    {
        return this.voltageDc;
    }

    public void setVoltageDc(Integer voltageDc)
    {
        this.voltageDc = voltageDc;
    }

    public Integer getYieldDay()
    {
        return this.yieldDay;
    }

    public void setYieldDay(Integer yieldDay)
    {
        this.yieldDay = yieldDay;
    }

    public Integer getYieldYesterday()
    {
        return this.yieldYesterday;
    }

    public void setYieldYesterday(Integer yieldYesterday)
    {
        this.yieldYesterday = yieldYesterday;
    }

    public Integer getYieldMonth()
    {
        return this.yieldMonth;
    }

    public void setYieldMonth(Integer yieldMonth)
    {
        this.yieldMonth = yieldMonth;
    }

    public Integer getYieldYear()
    {
        return this.yieldYear;
    }

    public void setYieldYear(Integer yieldYear)
    {
        this.yieldYear = yieldYear;
    }

    public Integer getYieldTotal()
    {
        return this.yieldTotal;
    }

    public void setYieldTotal(Integer yieldTotal)
    {
        this.yieldTotal = yieldTotal;
    }

    public Integer getConsumptionPower()
    {
        return this.consumptionPower;
    }

    public void setConsumptionPower(Integer consumptionPower)
    {
        this.consumptionPower = consumptionPower;
    }

    public Integer getConsumptionYieldDay()
    {
        return this.consumptionYieldDay;
    }

    public void setConsumptionYieldDay(Integer consumptionYieldDay)
    {
        this.consumptionYieldDay = consumptionYieldDay;
    }

    public Integer getConsumptionYieldYesterday()
    {
        return this.consumptionYieldYesterday;
    }

    public void setConsumptionYieldYesterday(Integer consumptionYieldYesterday)
    {
        this.consumptionYieldYesterday = consumptionYieldYesterday;
    }

    public Integer getConsumptionYieldMonth()
    {
        return this.consumptionYieldMonth;
    }

    public void setConsumptionYieldMonth(Integer consumptionYieldMonth)
    {
        this.consumptionYieldMonth = consumptionYieldMonth;
    }

    public Integer getConsumptionYieldYear()
    {
        return this.consumptionYieldYear;
    }

    public void setConsumptionYieldYear(Integer consumptionYieldYear)
    {
        this.consumptionYieldYear = consumptionYieldYear;
    }

    public Integer getConsumptionYieldTotal()
    {
        return this.consumptionYieldTotal;
    }

    public void setConsumptionYieldTotal(Integer consumptionYieldTotal)
    {
        this.consumptionYieldTotal = consumptionYieldTotal;
    }

    public Integer getTotalPower()
    {
        return this.totalPower;
    }

    public void setTotalPower(Integer totalPower)
    {
        this.totalPower = totalPower;
    }
}
