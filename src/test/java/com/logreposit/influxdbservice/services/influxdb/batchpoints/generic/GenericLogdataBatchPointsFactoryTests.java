package com.logreposit.influxdbservice.services.influxdb.batchpoints.generic;

import static org.assertj.core.api.Assertions.assertThat;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.IntegerFieldDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.ReadingDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.TagDto;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenericLogdataBatchPointsFactoryTests {
  private GenericLogdataBatchPointsFactory genericLogdataBatchPointsFactory;

  @BeforeEach
  public void setUp() {
    this.genericLogdataBatchPointsFactory = new GenericLogdataBatchPointsFactory();
  }

  @Test
  public void testCreateBatchPoints() throws GenericLogdataBatchPointsFactoryException {
    var deviceId = UUID.randomUUID().toString();

    var tag = new TagDto();

    tag.setName("key");
    tag.setValue("value");

    var field = new IntegerFieldDto();

    field.setName("voltage");
    field.setValue(24528L);

    var reading = new ReadingDto();

    reading.setDate(Instant.now());
    reading.setMeasurement("data");
    reading.setTags(List.of(tag));
    reading.setFields(List.of(field));

    var batchPoints =
        this.genericLogdataBatchPointsFactory.createBatchPoints(deviceId, List.of(reading));

    assertThat(batchPoints).isNotNull();
    assertThat(batchPoints.getPoints()).hasSize(1);
    assertThat(batchPoints.getPoints().get(0).toString())
        .isEqualTo(
            String.format(
                "Point [name=data, time=%d, tags={key=value}, precision=MILLISECONDS, fields={voltage=24528}]",
                reading.getDate().toEpochMilli()));
  }
}
