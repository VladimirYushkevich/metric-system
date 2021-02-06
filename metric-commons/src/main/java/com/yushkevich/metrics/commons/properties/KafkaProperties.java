package com.yushkevich.metrics.commons.properties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    public String topic;
    @JsonProperty("bootstrap-server-url")
    public String serverUrl;
    @JsonProperty("bootstrap-server-port")
    public Integer port;
}
