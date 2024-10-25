package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "datatype")
@JsonSubTypes({
  @JsonSubTypes.Type(value = StringFieldDto.class, name = "STRING"),
  @JsonSubTypes.Type(value = IntegerFieldDto.class, name = "INTEGER"),
  @JsonSubTypes.Type(value = FloatFieldDto.class, name = "FLOAT")
})
@Data
public class FieldDto {
  private String name;
  private DataType datatype;
}
