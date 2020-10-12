package com.logreposit.influxdbservice.services.influxdb.batchpoints.cotek;

import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.cotek.CotekSPSeriesLogData;
import com.logreposit.influxdbservice.communication.messaging.dtos.logrepositapi.rtlsdr.LacrosseTXLogData;
import com.logreposit.influxdbservice.services.influxdb.batchpoints.rtlsdr.LacrosseTXLogdataBatchPointsFactoryException;
import org.influxdb.dto.BatchPoints;

public interface CotekSPSeriesLogdataBatchPointsFactory
{
    BatchPoints createBatchPoints(String deviceId, CotekSPSeriesLogData cotekSPSeriesLogData) throws CotekSPSeriesLogdataBatchPointsFactoryException;
}
