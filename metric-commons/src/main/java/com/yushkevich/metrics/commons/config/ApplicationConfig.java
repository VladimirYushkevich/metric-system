package com.yushkevich.metrics.commons.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfig {
    @JsonProperty("kafka")
    private KafkaProperties kafkaProperties;

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }
}
