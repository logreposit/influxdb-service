package com.logreposit.influxdbservice.services.influxdb;

import org.influxdb.dto.BatchPoints;

public interface InfluxDBService {
  void insert(BatchPoints batchPoints);

  void createUser(String user, String password) throws InfluxDBServiceException;

  void createDatabase(String name, String readOnlyUserName) throws InfluxDBServiceException;
}
