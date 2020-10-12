package com.logreposit.influxdbservice.services.influxdb.batchpoints.s3200;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200.FroelingS3200LogData;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.s3200.FroelingS3200Reading;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class FroelingLambdatronicS3200LogdataBatchPointsFactoryImpl implements FroelingLambdatronicS3200LogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(FroelingLambdatronicS3200LogdataBatchPointsFactoryImpl.class);

    private static final String MEASUREMENT_NAME = "data";

    @Override
    public BatchPoints createBatchPoints(String deviceId, FroelingS3200LogData froelingS3200LogData) throws FroelingLambdatronicS3200LogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, froelingS3200LogData);

        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId);
        List<Point>         points             = createPoints(froelingS3200LogData);

        points.forEach(batchPointsBuilder::point);

        BatchPoints batchPoints = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, FroelingS3200LogData froelingS3200LogData) throws FroelingLambdatronicS3200LogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new FroelingLambdatronicS3200LogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (froelingS3200LogData == null)
        {
            logger.error("froelingS3200LogData is null!");
            throw new FroelingLambdatronicS3200LogdataBatchPointsFactoryException("froelingS3200LogData is null!");
        }

        if (froelingS3200LogData.getDate() == null)
        {
            logger.error("froelingS3200LogData.date is null!");
            throw new FroelingLambdatronicS3200LogdataBatchPointsFactoryException("froelingS3200LogData.date is null!");
        }
    }

    private static List<Point> createPoints(FroelingS3200LogData froelingS3200LogData)
    {
        if (CollectionUtils.isEmpty(froelingS3200LogData.getReadings()))
        {
            logger.warn("froelingS3200LogData.readings == empty.");

            return new ArrayList<>();
        }

        long        unixTimestamp = froelingS3200LogData.getDate().getTime();
        List<Point> points        = froelingS3200LogData.getReadings()
                                                        .stream()
                                                        .filter(Objects::nonNull)
                                                        .map(r -> createPoint(unixTimestamp, r))
                                                        .collect(Collectors.toList());

        return points;
    }

    private static Point createPoint(long unixTimestamp, FroelingS3200Reading froelingS3200Reading)
    {
        Map<String, String> tags = buildTags(froelingS3200Reading);

        Point point = Point.measurement(MEASUREMENT_NAME)
                           .time(unixTimestamp, TimeUnit.MILLISECONDS)
                           .tag(tags)
                           .addField("value", froelingS3200Reading.getValue())
                           .build();

        return point;
    }

    private static Map<String, String> buildTags(FroelingS3200Reading froelingS3200Reading)
    {
        Map<String, String> tags = new HashMap<>();

        if (!StringUtils.isEmpty(froelingS3200Reading.getAddress()))
        {
            tags.put("address", froelingS3200Reading.getAddress());
        }

        if (!StringUtils.isEmpty(froelingS3200Reading.getDescription()))
        {
            tags.put("description", froelingS3200Reading.getDescription());
        }

        if (!StringUtils.isEmpty(froelingS3200Reading.getUnit()))
        {
            tags.put("unit", froelingS3200Reading.getUnit());
        }

        return tags;
    }
}
