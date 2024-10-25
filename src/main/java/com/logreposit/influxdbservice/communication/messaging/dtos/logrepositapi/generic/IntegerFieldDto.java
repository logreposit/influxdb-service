package com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IntegerFieldDto extends FieldDto {
  private Long value;

  public IntegerFieldDto() {
    this.setDatatype(DataType.INTEGER);
  }
}
