package com.logreposit.influxdbservice.services.influxdb.batchpoints.generic;

import com.logreposit.influxdbservice.services.influxdb.batchpoints.BatchPointsFactoryException;

public class GenericLogdataBatchPointsFactoryException extends BatchPointsFactoryException
{
    public GenericLogdataBatchPointsFactoryException(String message)
    {
        super(message);
    }
}
