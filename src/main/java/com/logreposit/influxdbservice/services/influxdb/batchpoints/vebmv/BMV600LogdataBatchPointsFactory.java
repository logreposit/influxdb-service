package com.logreposit.influxdbservice.services.influxdb.batchpoints.vebmv;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv.BMV600LogData;
import org.influxdb.dto.BatchPoints;

public interface BMV600LogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, BMV600LogData bmv600LogData) throws BMV600LogdataBatchPointsFactoryException;
}
