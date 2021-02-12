# metric-system
![Master checks for Producer](https://github.com/VladimirYushkevich/metric-system/workflows/Master%20checks%20for%20Producer/badge.svg)
![Master checks for Consumer](https://github.com/VladimirYushkevich/metric-system/workflows/Master%20checks%20for%20Consumer/badge.svg)
![Master checks for DB](https://github.com/VladimirYushkevich/metric-system/workflows/Master%20checks%20for%20DB/badge.svg)

System that generates operating system metrics and passes the events through Aiven Kafka instance to Aiven PostgreSQL 
database.
For this, we have a Kafka producer which sends data to a Kafka topic, and a Kafka consumer storing the data to Aiven 
PostgreSQL database.

## Description
[metric-producer](metric-producer) and [metric-consumer](metric-consumer) are java applications within a multimodule project. 
They both depend on [metric-commons](metric-commons) library to avoid some code duplication. Producer and consumer have
following profiles:  
`local`   - Default properties.  
`docker`  - For running in docker.   
`testing` - For running on Aiven services.   
I didn't try combinations like local DB and Aiven Kafka but it should work as well.
Github Actions are used as a CI/CD and have very simple workflows after pushing commit to master branch:  
`Master checks for Producer` - builds producer image and publishes in [Producer Docker Registry](https://hub.docker.com/repository/docker/yushkevich/metric-producer)
`Master checks for Consumer` - builds producer image and publishes in [Consumer Docker Registry](https://hub.docker.com/repository/docker/yushkevich/metric-consumer)
`Master checks for DB` - Applies changes with Flyway from migration scripts.

## How to run locally
Producer and Consumer can be running as a docker images (yes, :) downloaded from public Registry) and fully ready to
connect to Aiven services.  
Run producer:
``` 
docker run yushkevich/metric-producer --profile=testing
```
Run consumer:
``` 
docker run yushkevich/metric-consumer --profile=testing
```

### Development
For local development all infrastructure components are running in docker.
Please navigate to `scripts` and follow instructions.
After `kafka`, `zookeper`, `schema-regustry` and `postgres` containers are up and running just build modules:
```
./gradlew metric-producer:build
./gradlew metric-consumer:build
```
Then just run it:
```
./gradlew metric-producer:run
./gradlew metric-consumer:run
```
