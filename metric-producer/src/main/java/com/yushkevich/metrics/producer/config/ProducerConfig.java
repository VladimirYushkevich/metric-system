package com.yushkevich.metrics.producer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yushkevich.metrics.commons.config.ApplicationConfig;

public class ProducerConfig extends ApplicationConfig {
    @JsonProperty("reporter")
    private ReporterProperties reporterProperties;

    public ReporterProperties getReporterProperties() {
        return reporterProperties;
    }
}
