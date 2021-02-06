package com.yushkevich.metrics.commons.properties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfig {
    @JsonProperty("kafka")
    public KafkaProperties kafkaProperties;
}
