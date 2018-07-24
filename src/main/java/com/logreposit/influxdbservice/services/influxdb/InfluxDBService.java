package com.logreposit.influxdbservice.services.influxdb;

import com.logreposit.influxdbservice.communication.messaging.dtos.logreposit.CmiLogData;

public interface InfluxDBService
{
    void insert         (String deviceId, CmiLogData cmiLogData) throws InfluxDBServiceException;
    void createUser     (String user, String password)           throws InfluxDBServiceException;
    void createDatabase (String name, String readOnlyUserName)   throws InfluxDBServiceException;
}
