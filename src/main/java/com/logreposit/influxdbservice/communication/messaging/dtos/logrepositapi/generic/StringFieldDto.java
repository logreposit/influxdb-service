package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StringFieldDto extends FieldDto
{
    private String value;

    public StringFieldDto() {
        this.setDatatype(DataType.STRING);
    }
}
