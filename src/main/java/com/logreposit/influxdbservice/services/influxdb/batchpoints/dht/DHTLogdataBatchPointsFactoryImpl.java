package com.logreposit.influxdbservice.services.influxdb.batchpoints.dht;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.dht.DHTLogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DHTLogdataBatchPointsFactoryImpl implements DHTLogdataBatchPointsFactory
{
    private static final String MEASUREMENT_NAME = "data";

    private static final Logger logger = LoggerFactory.getLogger(DHTLogdataBatchPointsFactoryImpl.class);

    @Override
    public BatchPoints createBatchPoints(String deviceId, DHTLogData dhtLogData) throws DHTLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, dhtLogData);

        Point               point              = createPoint(dhtLogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, DHTLogData dhtLogData) throws DHTLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new DHTLogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (dhtLogData == null)
        {
            logger.error("dhtLogData is null!");
            throw new DHTLogdataBatchPointsFactoryException("dhtLogData is null!");
        }

        if (dhtLogData.getDate() == null)
        {
            logger.error("dhtLogData.date is null!");
            throw new DHTLogdataBatchPointsFactoryException("ccs811LogData.date is null!");
        }

        if (dhtLogData.getTemperature() == null)
        {
            logger.error("dhtLogData.temperature is null!");
            throw new DHTLogdataBatchPointsFactoryException("dhtLogData.temperature is null!");
        }

        if (dhtLogData.getHumidity() == null)
        {
            logger.error("dhtLogData.humidity is null!");
            throw new DHTLogdataBatchPointsFactoryException("dhtLogData.humidity is null!");
        }
    }

    private static Point createPoint(DHTLogData dhtLogData)
    {
        long          unixTimestamp = dhtLogData.getDate().getTime();
        Point.Builder pointBuilder  = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);

        pointBuilder.addField("humidity", dhtLogData.getHumidity());
        pointBuilder.addField("temperature", dhtLogData.getTemperature());

        Point point = pointBuilder.build();

        return point;
    }
}
