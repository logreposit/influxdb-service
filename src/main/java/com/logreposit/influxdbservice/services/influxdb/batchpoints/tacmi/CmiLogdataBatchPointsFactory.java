package com.logreposit.influxdbservice.services.influxdb.batchpoints.tacmi;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.CmiLogData;
import org.influxdb.dto.BatchPoints;

public interface CmiLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, CmiLogData cmiLogData) throws CmiLogdataBatchPointsFactoryException;
}
