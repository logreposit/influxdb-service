package com.logreposit.influxdbservice;

/*
 * Created by dom on 10/21/17
 */

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTests
{
    @Test
    public void testDateStuff()
    {

        long uvrTime = 1508618047;

        uvrTime *= 1000;

        // 1508618047       20:34 Europe/Vienna

        Date date = new Date(uvrTime);

        LocalDateTime utcDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"));
        LocalDateTime zonedDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Vienna"));

        long zoned1Time = utcDate.toInstant(ZoneOffset.UTC).toEpochMilli();
        long zoned2Time = zonedDate.toInstant(ZoneOffset.UTC).toEpochMilli();

        long difference = zoned1Time - zoned2Time;

        long newtimestamp = uvrTime + difference;

        Date newDate = new Date(newtimestamp);

        System.out.print("");

    }
}
