package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FroelingS3200LogData
{
    private Date                       date;
    private List<FroelingS3200Reading> readings;

    public FroelingS3200LogData()
    {
        this.readings = new ArrayList<>();
    }
}
