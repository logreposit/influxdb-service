package com.logreposit.influxdbservice.services.influxdb.batchpoints.sds;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.sds.SolarLogLogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SolarLogLogdataBatchPointsFactoryImpl implements SolarLogLogdataBatchPointsFactory
{
    private static final Logger logger = LoggerFactory.getLogger(SolarLogLogdataBatchPointsFactoryImpl.class);

    private static final String MEASUREMENT_NAME = "data";

    @Override
    public BatchPoints createBatchPoints(String deviceId, SolarLogLogData solarLogLogData) throws SolarLogLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, solarLogLogData);

        Point               point              = getPoint(solarLogLogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static Point getPoint(SolarLogLogData solarLogLogData)
    {
        long          unixTimestamp = solarLogLogData.getDate().getTime();
        Point.Builder pointBuilder  = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);

        pointBuilder.addField("power_ac", solarLogLogData.getPowerAc());
        pointBuilder.addField("power_cd", solarLogLogData.getPowerDc());
        pointBuilder.addField("voltage_ac", solarLogLogData.getVoltageAc());
        pointBuilder.addField("voltage_dc", solarLogLogData.getVoltageDc());
        pointBuilder.addField("yield_day", solarLogLogData.getYieldDay());
        pointBuilder.addField("yield_yesterday", solarLogLogData.getYieldYesterday());
        pointBuilder.addField("yield_month", solarLogLogData.getYieldMonth());
        pointBuilder.addField("yield_year", solarLogLogData.getYieldYear());
        pointBuilder.addField("yield_total", solarLogLogData.getYieldTotal());
        pointBuilder.addField("consumption_power", solarLogLogData.getConsumptionPower());
        pointBuilder.addField("consumption_yield_day", solarLogLogData.getConsumptionYieldDay());
        pointBuilder.addField("consumption_yield_yesterday", solarLogLogData.getConsumptionYieldYesterday());
        pointBuilder.addField("consumption_yield_month", solarLogLogData.getConsumptionYieldMonth());
        pointBuilder.addField("consumption_yield_year", solarLogLogData.getConsumptionYieldYear());
        pointBuilder.addField("consumption_yield_total", solarLogLogData.getConsumptionYieldTotal());
        pointBuilder.addField("total_power", solarLogLogData.getTotalPower());

        Point point = pointBuilder.build();

        return point;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, SolarLogLogData solarLogLogData) throws SolarLogLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new SolarLogLogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (solarLogLogData == null)
        {
            logger.error("solarLogLogData is null!");
            throw new SolarLogLogdataBatchPointsFactoryException("solarLogLogData is null!");
        }

        if (solarLogLogData.getDate() == null)
        {
            logger.error("solarLogLogData.date is null!");
            throw new SolarLogLogdataBatchPointsFactoryException("solarLogLogData.date is null!");
        }
    }
}
