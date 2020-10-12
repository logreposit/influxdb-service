package com.logreposit.influxdbservice.services.influxdb.batchpoints.ccs811;

import com.logreposit.influxdbservice.services.influxdb.batchpoints.BatchPointsFactoryException;

public class CCS811LogdataBatchPointsFactoryException extends BatchPointsFactoryException
{
    public CCS811LogdataBatchPointsFactoryException(String message)
    {
        super(message);
    }
}
