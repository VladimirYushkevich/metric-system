package com.yushkevich.metrics.commons.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private String topic;
    @JsonProperty("bootstrap-server-url")
    private String serverUrl;
    @JsonProperty("bootstrap-server-port")
    private Integer port;

    public String getTopic() {
        return topic;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public Integer getPort() {
        return port;
    }
}
