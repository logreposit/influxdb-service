package com.logreposit.influxdbservice.exceptions;

public class LogrepositException extends Exception {
  public LogrepositException(String message) {
    super(message);
  }

  public LogrepositException(String message, Throwable cause) {
    super(message, cause);
  }
}
