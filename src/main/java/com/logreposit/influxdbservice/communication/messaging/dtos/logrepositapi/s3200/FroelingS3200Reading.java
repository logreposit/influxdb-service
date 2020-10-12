package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FroelingS3200Reading
{
    private String address;
    private int    value;
    private String unit;
    private String description;
}
