package com.logreposit.influxdbservice.services.influxdb.batchpoints.ccs811;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.ccs811.CCS811LogData;
import org.influxdb.dto.BatchPoints;

public interface CCS811LogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, CCS811LogData ccs811LogData) throws CCS811LogdataBatchPointsFactoryException;
}
