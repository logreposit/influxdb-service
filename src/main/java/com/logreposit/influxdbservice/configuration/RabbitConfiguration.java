package com.logreposit.influxdbservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@Configuration
@ConfigurationProperties(value = "influxdbservice.communication.messaging.rabbit")
public class RabbitConfiguration
{
    @NotBlank
    private String queue;

    public String getQueue()
    {
        return this.queue;
    }

    public void setQueue(String queue)
    {
        this.queue = queue;
    }
}
