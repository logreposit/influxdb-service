package com.logreposit.influxdbservice.services.influxdb.batchpoints.ccs811;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.ccs811.CCS811LogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CCS811LogdataBatchPointsFactoryImpl implements CCS811LogdataBatchPointsFactory
{
    private static final String MEASUREMENT_NAME = "data";

    private static final Logger logger = LoggerFactory.getLogger(CCS811LogdataBatchPointsFactoryImpl.class);

    @Override
    public BatchPoints createBatchPoints(String deviceId, CCS811LogData ccs811LogData) throws CCS811LogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, ccs811LogData);

        Point               point              = createPoint(ccs811LogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, CCS811LogData ccs811LogData) throws CCS811LogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new CCS811LogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (ccs811LogData == null)
        {
            logger.error("ccs811LogData is null!");
            throw new CCS811LogdataBatchPointsFactoryException("ccs811LogData is null!");
        }

        if (ccs811LogData.getDate() == null)
        {
            logger.error("ccs811LogData.date is null!");
            throw new CCS811LogdataBatchPointsFactoryException("ccs811LogData.date is null!");
        }

        if (ccs811LogData.getEco2() == null)
        {
            logger.error("ccs811LogData.inputVoltage is null!");
            throw new CCS811LogdataBatchPointsFactoryException("ccs811LogData.inputVoltage is null!");
        }

        if (ccs811LogData.getTvoc() == null)
        {
            logger.error("ccs811LogData.outputVoltage is null!");
            throw new CCS811LogdataBatchPointsFactoryException("ccs811LogData.outputVoltage is null!");
        }
    }

    private static Point createPoint(CCS811LogData ccs811LogData)
    {
        long          unixTimestamp = ccs811LogData.getDate().getTime();
        Point.Builder pointBuilder  = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);

        pointBuilder.addField("eco2", ccs811LogData.getEco2());
        pointBuilder.addField("tvoc", ccs811LogData.getTvoc());

        Point point = pointBuilder.build();

        return point;
    }
}
