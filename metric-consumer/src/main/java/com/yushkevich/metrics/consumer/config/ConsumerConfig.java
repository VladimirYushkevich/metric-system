package com.yushkevich.metrics.consumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yushkevich.metrics.commons.config.ApplicationConfig;

public class ConsumerConfig extends ApplicationConfig {
    @JsonProperty("jdbc")
    private PersistenceProperties persistenceProperties;

    public PersistenceProperties getPersistenceProperties() {
        return persistenceProperties;
    }
}
