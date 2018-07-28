package com.logreposit.influxdbservice.services.influxdb.batchpoints.vebmv;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.vebmv.BMV600LogData;
import org.apache.commons.lang3.BooleanUtils;
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
        Point.Builder pointBuilder = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);
        int           alarm        = convertBooleanToInt(bmv600LogData.getAlarm());
        int           relay        = convertBooleanToInt(bmv600LogData.getRelay());

        pointBuilder.addField("alarm", alarm);
        pointBuilder.addField("relay", relay);
        pointBuilder.addField("battery_voltage", bmv600LogData.getBatteryVoltage());
        pointBuilder.addField("starter_battery_voltage", bmv600LogData.getStarterBatteryVoltage());
        pointBuilder.addField("current", bmv600LogData.getCurrent());
        pointBuilder.addField("state_of_charge", bmv600LogData.getStateOfCharge());
        pointBuilder.addField("consumed_energy", bmv600LogData.getConsumedEnergy());
        pointBuilder.addField("time_to_go", bmv600LogData.getTimeToGo());

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

    private static int convertBooleanToInt(Boolean value)
    {
        boolean bool         = BooleanUtils.isTrue(value);
        int     integerValue = bool ? 1 : 0;

        return integerValue;
    }
}
