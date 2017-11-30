package com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.retry;

import org.springframework.stereotype.Component;

@Component
public class BackoffRetryStrategy implements RetryStrategy
{
    private static final String RETRY_EXCHANGE_10000_NAME  = "x_retry_10000";
    private static final String RETRY_EXCHANGE_30000_NAME  = "x_retry_30000";
    private static final String RETRY_EXCHANGE_300000_NAME = "x_retry_300000";

    @Override
    public String getExchangeName(long errorCount, String defaultExchangeName)
    {
        if (errorCount <= 5)
            return RETRY_EXCHANGE_10000_NAME;

        if (errorCount <= 10)
            return RETRY_EXCHANGE_30000_NAME;

        if (errorCount <= 15)
            return RETRY_EXCHANGE_300000_NAME;

        return defaultExchangeName;
    }
}
