FROM adoptopenjdk/openjdk11:latest
WORKDIR ./metric-producer
ADD /build/libs/metric-producer.jar metric-producer.jar
ENTRYPOINT ["java","-jar","metric-producer.jar"]