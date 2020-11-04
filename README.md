# influxdb-service

| branch | CI build | test coverage |
|--------|:--------:|--------------:|
| master  | [![CircleCI](https://circleci.com/gh/logreposit/influxdb-service/tree/master.svg?style=shield)](https://circleci.com/gh/logreposit/influxdb-service/tree/master)   | [![codecov.io](https://codecov.io/gh/logreposit/influxdb-service/branch/master/graphs/badge.svg)](https://codecov.io/gh/logreposit/influxdb-service/branch/master/graphs/badge.svg)   |
| develop | [![CircleCI](https://circleci.com/gh/logreposit/influxdb-service/tree/develop.svg?style=shield)](https://circleci.com/gh/logreposit/influxdb-service/tree/develop) | [![codecov.io](https://codecov.io/gh/logreposit/influxdb-service/branch/develop/graphs/badge.svg)](https://codecov.io/gh/logreposit/influxdb-service/branch/develop/graphs/badge.svg) |

## Service Description

TODO

## Environment Requirements

* InfluxDB

## Configuration

This service has to be configured via environment variables. The data which it processes is read from a RabbitMQ queue.

|Environment Variable Name                             | default value         |
|------------------------------------------------------|-----------------------|
| SPRING_RABBITMQ_HOST                                 | localhost             |
| SPRING_RABBITMQ_PORT                                 | 5672                  |
| SPRING_RABBITMQ_USERNAME                             | guest                 |
| SPRING_RABBITMQ_PASSWORD                             | guest                 |
| INFLUXDBSERVICE_COMMUNICATION_MESSAGING_RABBIT_QUEUE | q.influxdb_service    |
| INFLUXDBSERVICE_COMMUNICATION_INFLUX_URL             | http://localhost:8086 |
| INFLUXDBSERVICE_COMMUNICATION_INFLUX_USERNAME        | admin                 |
| INFLUXDBSERVICE_COMMUNICATION_INFLUX_PASSWORD        | admin                 |

## TODO

Description of service, API Documentation and setup along with maintenance instructions
