package com.logreposit.influxdbservice.communication.messaging.exceptions;

import com.logreposit.influxdbservice.exceptions.LogrepositException;

public class MessagingException extends LogrepositException {
  public MessagingException(String message) {
    super(message);
  }

  public MessagingException(String message, Throwable cause) {
    super(message, cause);
  }
}
