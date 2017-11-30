package com.logreposit.influxdbservice.services.influxdb;

import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.CmiLogData;

public interface InfluxDBService
{
    void insert(String organizationId, String deviceId, CmiLogData cmiLogData) throws InfluxDBServiceException;
}
