FROM adoptopenjdk/openjdk11:latest
WORKDIR ./metric-consumer
ADD /build/libs/metric-consumer.jar metric-consumer.jar
ENTRYPOINT ["java","-jar","metric-consumer.jar"]