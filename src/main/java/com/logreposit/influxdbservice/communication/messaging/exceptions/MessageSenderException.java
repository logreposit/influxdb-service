package com.logreposit.influxdbservice.communication.messaging.exceptions;

public class MessageSenderException extends MessagingException {
  public MessageSenderException(String message, Throwable cause) {
    super(message, cause);
  }
}
