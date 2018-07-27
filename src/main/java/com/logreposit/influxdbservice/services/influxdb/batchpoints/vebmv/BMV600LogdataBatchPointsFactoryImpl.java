package com.logreposit.influxdbservice.services.influxdb.batchpoints.vebmv;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv.BMV600LogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BMV600LogdataBatchPointsFactoryImpl implements BMV600LogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(BMV600LogdataBatchPointsFactoryImpl.class);

    private static final String MEASUREMENT_NAME   = "data";
    private static final String VOLTAGE_FIELD_NAME = "voltage";
    private static final String CURRENT_FIELD_NAME = "current";
    private static final String RELAY_FIELD_NAME   = "relay";
    private static final String ALARM_FIELD_NAME   = "alarm";

    @Override
    public BatchPoints createBatchPoints(String deviceId, BMV600LogData bmv600LogData) throws BMV600LogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, bmv600LogData);

        Point               point              = getPoint(bmv600LogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static Point getPoint(BMV600LogData bmv600LogData)
    {
        long          unixTimestamp = bmv600LogData.getDate().getTime();
        Point.Builder pointBuilder  = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);

        pointBuilder.addField(VOLTAGE_FIELD_NAME, bmv600LogData.getVoltage());
        pointBuilder.addField(CURRENT_FIELD_NAME, bmv600LogData.getCurrent());
        pointBuilder.addField(RELAY_FIELD_NAME, bmv600LogData.getRelay());
        pointBuilder.addField(ALARM_FIELD_NAME, bmv600LogData.getAlarm());

        Point point = pointBuilder.build();

        return point;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, BMV600LogData bmv600LogData) throws BMV600LogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new BMV600LogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (bmv600LogData == null)
        {
            logger.error("bmv600LogData is null!");
            throw new BMV600LogdataBatchPointsFactoryException("cmiLogData is null!");
        }

        if (bmv600LogData.getDate() == null)
        {
            logger.error("bmv600LogData.date is null!");
            throw new BMV600LogdataBatchPointsFactoryException("cmiLogData.date is null!");
        }
    }
}
