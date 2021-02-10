package com.yushkevich.metrics.producer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReporterProperties {
    @JsonProperty("poll-interval")
    private Integer pollInterval;

    public Integer getPollInterval() {
        return pollInterval;
    }
}
