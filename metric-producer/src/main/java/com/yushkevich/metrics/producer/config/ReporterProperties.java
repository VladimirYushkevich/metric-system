package com.yushkevich.metrics.producer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReporterProperties {
    @JsonProperty("poll-interval")
    private Integer pollInterval;
    private String env;

    public Integer getPollInterval() {
        return pollInterval;
    }

    public String getEnv() {
        return env;
    }
}
