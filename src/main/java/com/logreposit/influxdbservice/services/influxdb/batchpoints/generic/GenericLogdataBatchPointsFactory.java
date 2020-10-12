package com.logreposit.influxdbservice.services.influxdb.batchpoints.generic;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.ReadingDto;
import org.influxdb.dto.BatchPoints;

import java.util.List;

public interface GenericLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, List<ReadingDto> readings) throws GenericLogdataBatchPointsFactoryException;
}
