package com.logreposit.influxdbservice.services.influxdb.batchpoints.rtlsdr;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.rtlsdr.LacrosseTXLogData;
import org.influxdb.dto.BatchPoints;

public interface LacrosseTXLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, LacrosseTXLogData lacrosseTXLogData) throws LacrosseTXLogdataBatchPointsFactoryException;
}
