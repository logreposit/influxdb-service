package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReadingDto
{
    private Instant date;
    private String measurement;
    private List<TagDto> tags;
    private List<FieldDto> fields;

    public ReadingDto() {
        this.tags = new ArrayList<>();
        this.fields = new ArrayList<>();
    }
}
