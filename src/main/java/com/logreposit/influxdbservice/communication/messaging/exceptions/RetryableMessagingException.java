package com.logreposit.influxdbservice.communication.messaging.exceptions;

public class RetryableMessagingException extends MessagingException {
  public RetryableMessagingException(String message, Throwable cause) {
    super(message, cause);
  }
}
