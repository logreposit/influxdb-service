package com.logreposit.influxdbservice.services.influxdb.batchpoints.cotek;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.cotek.CotekSPSeriesLogData;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CotekSPSeriesLogdataBatchPointsFactoryImpl implements CotekSPSeriesLogdataBatchPointsFactory
{
    private static final String MEASUREMENT_NAME = "data";

    private static final Logger logger = LoggerFactory.getLogger(CotekSPSeriesLogdataBatchPointsFactoryImpl.class);

    @Override
    public BatchPoints createBatchPoints(String deviceId, CotekSPSeriesLogData cotekSPSeriesLogData) throws CotekSPSeriesLogdataBatchPointsFactoryException
    {
        checkIfInputIsValidOtherwiseThrowException(deviceId, cotekSPSeriesLogData);

        Point               point              = createPoint(cotekSPSeriesLogData);
        BatchPoints.Builder batchPointsBuilder = BatchPoints.database(deviceId).point(point);
        BatchPoints         batchPoints        = batchPointsBuilder.build();

        return batchPoints;
    }

    private static void checkIfInputIsValidOtherwiseThrowException(String deviceId, CotekSPSeriesLogData cotekSPSeriesLogData) throws CotekSPSeriesLogdataBatchPointsFactoryException
    {
        if (StringUtils.isBlank(deviceId))
        {
            logger.error("deviceId is blank!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("deviceId is blank!");
        }

        if (cotekSPSeriesLogData == null)
        {
            logger.error("cotekSPSeriesLogData is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData is null!");
        }

        if (cotekSPSeriesLogData.getDate() == null)
        {
            logger.error("cotekSPSeriesLogData.date is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.date is null!");
        }

        if (cotekSPSeriesLogData.getInputVoltage() == null)
        {
            logger.error("cotekSPSeriesLogData.inputVoltage is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.inputVoltage is null!");
        }

        if (cotekSPSeriesLogData.getOutputVoltage() == null)
        {
            logger.error("cotekSPSeriesLogData.outputVoltage is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.outputVoltage is null!");
        }

        if (cotekSPSeriesLogData.getOutputFrequency() == null)
        {
            logger.error("cotekSPSeriesLogData.outputFrequency is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.outputFrequency is null!");
        }

        if (cotekSPSeriesLogData.getOutputCurrent() == null)
        {
            logger.error("cotekSPSeriesLogData.outputCurrent is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.outputCurrent is null!");
        }

        if (cotekSPSeriesLogData.getOutputPower() == null)
        {
            logger.error("cotekSPSeriesLogData.outputPower is null!");
            throw new CotekSPSeriesLogdataBatchPointsFactoryException("cotekSPSeriesLogData.outputPower is null!");
        }
    }

    private static Point createPoint(CotekSPSeriesLogData cotekSPSeriesLogData)
    {
        long          unixTimestamp = cotekSPSeriesLogData.getDate().getTime();
        Point.Builder pointBuilder  = Point.measurement(MEASUREMENT_NAME).time(unixTimestamp, TimeUnit.MILLISECONDS);

        pointBuilder.addField("input_voltage", cotekSPSeriesLogData.getInputVoltage());
        pointBuilder.addField("output_voltage", cotekSPSeriesLogData.getOutputVoltage());
        pointBuilder.addField("output_frequency", cotekSPSeriesLogData.getOutputFrequency());
        pointBuilder.addField("output_current", cotekSPSeriesLogData.getOutputCurrent());
        pointBuilder.addField("output_power", cotekSPSeriesLogData.getOutputPower());

        Point point = pointBuilder.build();

        return point;
    }
}
