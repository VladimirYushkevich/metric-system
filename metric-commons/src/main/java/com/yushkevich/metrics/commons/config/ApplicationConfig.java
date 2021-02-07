package com.yushkevich.metrics.commons.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfig {
    @JsonProperty("kafka")
    public KafkaProperties kafkaProperties;
}
