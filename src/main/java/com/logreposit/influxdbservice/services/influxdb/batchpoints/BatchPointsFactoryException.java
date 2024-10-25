package com.logreposit.influxdbservice.services.influxdb.batchpoints;

import com.logreposit.influxdbservice.exceptions.LogrepositException;

public class BatchPointsFactoryException extends LogrepositException {
  public BatchPointsFactoryException(String message) {
    super(message);
  }
}
