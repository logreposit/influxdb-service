package com.logreposit.influxdbservice.configuration;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(value = "influxdbservice.communication.influx")
public class InfluxDBConfiguration {
  private String url;
  private String username;
  private String password;

  @Bean
  public InfluxDB influxDb() {
    InfluxDB influxDB = InfluxDBFactory.connect(this.url, this.username, this.password);

    return influxDB;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
