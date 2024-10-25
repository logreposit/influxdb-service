package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FloatFieldDto extends FieldDto {
  private Double value;

  public FloatFieldDto() {
    this.setDatatype(DataType.FLOAT);
  }
}
