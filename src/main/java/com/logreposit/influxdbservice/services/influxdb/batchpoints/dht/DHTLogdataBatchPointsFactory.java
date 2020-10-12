package com.logreposit.influxdbservice.services.influxdb.batchpoints.dht;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.dht.DHTLogData;
import org.influxdb.dto.BatchPoints;

public interface DHTLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, DHTLogData dhtLogData) throws DHTLogdataBatchPointsFactoryException;
}
