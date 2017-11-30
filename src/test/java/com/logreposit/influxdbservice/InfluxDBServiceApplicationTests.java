package com.logreposit.influxdbservice;

import com.logreposit.influxdbservice.communication.messaging.rabbitmq.listener.RabbitMessageListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfluxDBServiceApplicationTests
{
    @Test
    public void contextLoads()
    {
    }

}
