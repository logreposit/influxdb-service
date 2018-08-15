package com.logreposit.influxdbservice.services.influxdb.batchpoints.sds;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.sds.SolarLogLogData;
import org.influxdb.dto.BatchPoints;

public interface SolarLogLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, SolarLogLogData solarLogLogData) throws SolarLogLogdataBatchPointsFactoryException;
}
