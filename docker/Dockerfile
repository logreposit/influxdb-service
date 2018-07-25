FROM openjdk:10-jre

WORKDIR /opt/logreposit-influxdb-service

COPY run.sh      /opt/logreposit-influxdb-service/run.sh
COPY app.jar     /opt/logreposit-influxdb-service/app.jar

RUN chmod 755    /opt/logreposit-influxdb-service/run.sh

ENTRYPOINT ["/opt/logreposit-influxdb-service/run.sh"]
