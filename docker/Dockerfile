FROM eclipse-temurin:21-alpine

WORKDIR /opt/logreposit/logreposit-influxdb-service

COPY run.sh    /opt/logreposit/logreposit-influxdb-service/run.sh
COPY app.jar   /opt/logreposit/logreposit-influxdb-service/app.jar
RUN  chmod 755 /opt/logreposit/logreposit-influxdb-service/run.sh

ENTRYPOINT ["/opt/logreposit/logreposit-influxdb-service/run.sh"]
