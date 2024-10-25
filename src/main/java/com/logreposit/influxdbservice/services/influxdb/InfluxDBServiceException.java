package com.logreposit.influxdbservice.services.influxdb;

import com.logreposit.influxdbservice.exceptions.LogrepositException;

public class InfluxDBServiceException extends LogrepositException {
  public InfluxDBServiceException(String message) {
    super(message);
  }
}
