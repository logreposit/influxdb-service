#!/bin/sh

java -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar /opt/influxdb-service/app.jar
