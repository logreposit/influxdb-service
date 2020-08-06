package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ReadingDto
{
    private Instant date;
    private String measurement;
    private Map<String, String> tags;
    private List<FieldDto> fields;

    public ReadingDto() {
        this.tags = new HashMap<>();
        this.fields = new ArrayList<>();
    }
}
