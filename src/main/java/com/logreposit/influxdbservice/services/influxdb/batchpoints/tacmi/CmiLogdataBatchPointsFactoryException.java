package com.logreposit.influxdbservice.services.influxdb.batchpoints.tacmi;

import com.logreposit.influxdbservice.services.influxdb.batchpoints.BatchPointsFactoryException;

public class CmiLogdataBatchPointsFactoryException extends BatchPointsFactoryException
{
    public CmiLogdataBatchPointsFactoryException(String message)
    {
        super(message);
    }
}
