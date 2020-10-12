package com.logreposit.influxdbservice.services.influxdb.batchpoints.dht;

import com.logreposit.influxdbservice.services.influxdb.batchpoints.BatchPointsFactoryException;

public class DHTLogdataBatchPointsFactoryException extends BatchPointsFactoryException
{
    public DHTLogdataBatchPointsFactoryException(String message)
    {
        super(message);
    }
}
