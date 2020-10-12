package com.logreposit.influxdbservice.services.influxdb.batchpoints.s3200;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200.FroelingS3200LogData;
import org.influxdb.dto.BatchPoints;

public interface FroelingLambdatronicS3200LogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, FroelingS3200LogData froelingS3200LogData) throws FroelingLambdatronicS3200LogdataBatchPointsFactoryException;
}
