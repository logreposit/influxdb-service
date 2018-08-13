package com.logreposit.influxdbservice.services.influxdb.batchpoints.rtlsdr;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.rtlsdr.LacrosseTXLogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class LacrosseTXLogdataBatchPointsFactoryImpl implements LacrosseTXLogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(LacrosseTXLogdataBatchPointsFactoryImpl.class);

    @Override
    public BatchPoints createBatchPoints(String deviceId, LacrosseTXLogData lacrosseTXLogData) throws LacrosseTXLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, lacrosseTXLogData);

        Point               point              = createPoint(lacrosseTXLogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, LacrosseTXLogData lacrosseTXLogData) throws LacrosseTXLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new LacrosseTXLogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (lacrosseTXLogData == null)
        {
            logger.error("lacrosseTXLogData is null!");
            throw new LacrosseTXLogdataBatchPointsFactoryException("lacrosseTXLogData is null!");
        }

        if (lacrosseTXLogData.getDate() == null)
        {
            logger.error("lacrosseTXLogData.date is null!");
            throw new LacrosseTXLogdataBatchPointsFactoryException("lacrosseTXLogData.date is null!");
        }

        if (StringUtils.isBlank(lacrosseTXLogData.getLocation()))
        {
            logger.error("lacrosseTXLogData.location is blank!");
            throw new LacrosseTXLogdataBatchPointsFactoryException("lacrosseTXLogData.location is blank!");
        }

        if (lacrosseTXLogData.getSensorId() == null)
        {
            logger.error("lacrosseTXLogData.sensorId is null!");
            throw new LacrosseTXLogdataBatchPointsFactoryException("lacrosseTXLogData.sensorId is null!");
        }
    }

    private static Point createPoint(LacrosseTXLogData lacrosseTXLogData)
    {
        long                unixTimestamp = lacrosseTXLogData.getDate().getTime();
        Map<String, String> tags          = createTags(lacrosseTXLogData);

        Point.Builder pointBuilder = Point.measurement("measurement")
                                          .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                          .tag(tags);

        if (lacrosseTXLogData.getBatteryNew() != null)
        {
            int integerValue = lacrosseTXLogData.getBatteryNew() ? 1 : 0;

            pointBuilder.addField("battery_new", integerValue);
        }

        if (lacrosseTXLogData.getBatteryLow() != null)
        {
            int integerValue = lacrosseTXLogData.getBatteryLow() ? 1 : 0;

            pointBuilder.addField("battery_low", integerValue);
        }

        if (lacrosseTXLogData.getTemperature() != null)
        {
            pointBuilder.addField("temperature", lacrosseTXLogData.getTemperature());
        }

        if (lacrosseTXLogData.getHumidity() != null)
        {
            pointBuilder.addField("humidity", lacrosseTXLogData.getHumidity());
        }

        Point point = pointBuilder.build();

        return point;
    }

    private static Map<String, String> createTags(LacrosseTXLogData lacrosseTXLogData)
    {
        Map<String, String> tags = new HashMap<>();

        tags.put("location", lacrosseTXLogData.getLocation());
        tags.put("sensor_id", String.valueOf(lacrosseTXLogData.getSensorId()));

        return tags;
    }
}
