FROM openjdk:8-jre-alpine

WORKDIR /root

COPY influxdb-service.jar  /opt/influxdb-service/app.jar
COPY run.sh /opt/influxdb-service/run.sh

RUN chmod 755 /opt/influxdb-service/run.sh && mkdir -p /var/log/logreposit/influxdb-service

ENTRYPOINT ["/opt/influxdb-service/run.sh"]
