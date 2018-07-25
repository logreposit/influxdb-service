package com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.retry;

import org.springframework.stereotype.Component;

@Component
public class BackoffRetryStrategy implements RetryStrategy
{
    public static final int[] RETRY_INTERVALS = {10000, 30000, 300000};

    @Override
    public String getExchangeName(long errorCount, String defaultExchangeName)
    {
        if (errorCount <= 5)
            return getExchangeNameForRetryInterval(RETRY_INTERVALS[0]);

        if (errorCount <= 10)
            return getExchangeNameForRetryInterval(RETRY_INTERVALS[1]);

        if (errorCount <= 15)
            return getExchangeNameForRetryInterval(RETRY_INTERVALS[2]);

        return defaultExchangeName;
    }

    public static String getExchangeNameForRetryInterval(int retryInterval)
    {
        return "retry.x." + retryInterval;
    }
}
