package com.logreposit.influxdbservice.services.influxdb.batchpoints.tacmi;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.AbstractIO;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.AnalogLoggingValue;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.CmiLogData;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.DigitalLoggingValue;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.Input;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.tacmi.Output;
import com.logreposit.influxdbservice.services.influxdb.InfluxDBServiceException;
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
import java.util.concurrent.TimeUnit;

@Component
public class CmiLogdataBatchPointsFactoryImpl implements CmiLogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(CmiLogdataBatchPointsFactoryImpl.class);

    private static final String INPUT_MEASUREMENT_NAME = "input";
    private static final String OUTPUT_MEASUREMENT_NAME = "output";
    private static final String ANALOG_LOGGING_MEASUREMENT_NAME = "analog_logging";
    private static final String DIGITAL_LOGGING_MEASUREMENT_NAME = "digital_logging";

    @Override
    public BatchPoints createBatchPoints(String deviceId, CmiLogData cmiLogData) throws CmiLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, cmiLogData);

        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId);
        List<Point>         points             = createPoints(cmiLogData);

        for (Point point : points)
        {
            batchPointsBuilder.point(point);
        }

        BatchPoints batchPoints = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, CmiLogData cmiLogData) throws CmiLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new CmiLogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (cmiLogData == null)
        {
            logger.error("cmiLogData is null!");
            throw new CmiLogdataBatchPointsFactoryException("cmiLogData is null!");
        }

        if (cmiLogData.getDate() == null)
        {
            logger.error("cmiLogData.date is null!");
            throw new CmiLogdataBatchPointsFactoryException("cmiLogData.date is null!");
        }
    }

    private static List<Point> createPoints(CmiLogData cmiLogData)
    {
        List<Point> points = new ArrayList<>();
        long unixTimestamp = cmiLogData.getDate().getTime();

        if (!CollectionUtils.isEmpty(cmiLogData.getInputs()))
        {
            for (Input input : cmiLogData.getInputs())
            {
                Map<String, String> tags = getInputTags(input);

                Point.Builder pointBuilder = Point.measurement(INPUT_MEASUREMENT_NAME)
                                                  .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                                  .tag(tags);

                if (input.getValue() != null)
                {
                    pointBuilder.addField("value", input.getValue());
                }

                if (input.getRasState() != null)
                {
                    pointBuilder.addField("ras_state", input.getRasState().toString().toLowerCase());
                }

                Point point = pointBuilder.build();

                points.add(point);
            }
        }

        if (!CollectionUtils.isEmpty(cmiLogData.getOutputs()))
        {
            for (Output output : cmiLogData.getOutputs())
            {
                Map<String, String> tags = getOutputTags(output);

                Point.Builder pointBuilder = Point.measurement(OUTPUT_MEASUREMENT_NAME)
                                                  .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                                  .tag(tags);

                if (output.getValue() != null)
                {
                    pointBuilder.addField("value", output.getValue());
                }

                if (output.getState() != null)
                {
                    pointBuilder.addField("state", output.getState());
                }

                Point point = pointBuilder.build();

                points.add(point);
            }
        }

        if (!CollectionUtils.isEmpty(cmiLogData.getAnalogLoggingValues()))
        {
            for (AnalogLoggingValue analogLoggingValue : cmiLogData.getAnalogLoggingValues())
            {
                Map<String, String> tags = getAnalogLoggingValueTags(analogLoggingValue);

                Point.Builder pointBuilder = Point.measurement(ANALOG_LOGGING_MEASUREMENT_NAME)
                                                  .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                                  .tag(tags);

                if (analogLoggingValue.getValue() != null)
                {
                    pointBuilder.addField("value", analogLoggingValue.getValue());
                }

                Point point = pointBuilder.build();

                points.add(point);
            }
        }

        if (!CollectionUtils.isEmpty(cmiLogData.getDigitalLoggingValues()))
        {
            for (DigitalLoggingValue digitalLoggingValue : cmiLogData.getDigitalLoggingValues())
            {
                Map<String, String> tags = getDigitalLoggingValueTags(digitalLoggingValue);

                Point.Builder pointBuilder = Point.measurement(DIGITAL_LOGGING_MEASUREMENT_NAME)
                                                  .time(unixTimestamp, TimeUnit.MILLISECONDS)
                                                  .tag(tags);

                if (digitalLoggingValue.getValue() != null)
                {
                    pointBuilder.addField("value", digitalLoggingValue.getValue());
                }

                Point point = pointBuilder.build();

                points.add(point);
            }
        }

        return points;
    }

    private static Map<String, String> getInputTags(Input input)
    {
        Map<String, String> tags = new HashMap<>(getAbstractIOTags(input));

        return tags;
    }

    private static Map<String, String> getOutputTags(Output output)
    {
        Map<String, String> tags = new HashMap<>(getAbstractIOTags(output));

        return tags;
    }

    private static Map<String, String> getAnalogLoggingValueTags(AnalogLoggingValue analogLoggingValue)
    {
        Map<String, String> tags = new HashMap<>(getAbstractIOTags(analogLoggingValue));

        return tags;
    }

    private static Map<String, String> getDigitalLoggingValueTags(DigitalLoggingValue digitalLoggingValue)
    {
        Map<String, String> tags = new HashMap<>(getAbstractIOTags(digitalLoggingValue));

        return tags;
    }

    private static Map<String, String> getAbstractIOTags(AbstractIO abstractIO)
    {
        Map<String, String> tags = new HashMap<>();

        if (abstractIO == null)
            return tags;

        if (abstractIO.getNumber() != null)
        {
            tags.put("number", abstractIO.getNumber().toString());
        }

        if (abstractIO.getSignal() != null)
        {
            tags.put("signal", abstractIO.getSignal().toString().toLowerCase());
        }

        if (abstractIO.getUnit() != null)
        {
            tags.put("unit", abstractIO.getUnit().toString().toLowerCase());
        }

        return tags;
    }
}
