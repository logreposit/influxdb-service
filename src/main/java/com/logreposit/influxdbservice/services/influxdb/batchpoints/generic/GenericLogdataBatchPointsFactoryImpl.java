package com.logreposit.influxdbservice.services.influxdb.batchpoints.generic;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.FieldDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.FloatFieldDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.IntegerFieldDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.ReadingDto;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.generic.StringFieldDto;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GenericLogdataBatchPointsFactoryImpl implements GenericLogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(GenericLogdataBatchPointsFactoryImpl.class);

    @Override
    public BatchPoints createBatchPoints(String deviceId, List<ReadingDto> readings) throws GenericLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId);

        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId);

        for (ReadingDto reading : readings) {
            batchPointsBuilder.point(this.createPoint(reading));
        }

        BatchPoints batchPoints = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId) throws GenericLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new GenericLogdataBatchPointsFactoryException("deviceId is blank!");
        }
    }

    private Point createPoint(ReadingDto reading) throws GenericLogdataBatchPointsFactoryException
    {
        long unixTimestamp = reading.getDate().toEpochMilli();

        Point.Builder pointBuilder  = Point.measurement(reading.getMeasurement())
                                           .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                           .tag(reading.getTags());

        for (FieldDto field : reading.getFields()) {
            if (field instanceof FloatFieldDto) {
                pointBuilder.addField(field.getName(), ((FloatFieldDto) field).getValue());
            }
            else if (field instanceof IntegerFieldDto) {
                pointBuilder.addField(field.getName(), ((IntegerFieldDto) field).getValue());
            }
            else if (field instanceof StringFieldDto) {
                pointBuilder.addField(field.getName(), ((StringFieldDto) field).getValue());
            }
            else {
                throw new GenericLogdataBatchPointsFactoryException(String.format("Got unknown FieldDto with DataType '%s'", field.getDatatype()));
            }
        }

        return pointBuilder.build();
    }
}
