package com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.retry;

public interface RetryStrategy
{
    String getExchangeName(long errorCount, String errorExchangeName);
}
