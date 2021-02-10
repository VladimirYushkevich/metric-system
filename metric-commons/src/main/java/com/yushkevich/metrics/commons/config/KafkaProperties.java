package com.yushkevich.metrics.commons.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private String topic;
    @JsonProperty(value = "bootstrap-server-url")
    private String serverUrl;
    @JsonProperty(value = "bootstrap-server-port")
    private Integer port;
    @JsonProperty(value = "ssl-enabled")
    private Boolean isSslEnabled;
    @JsonProperty(value = "certstore-password")
    private String certStorePassword;

    public String getTopic() {
        return topic;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public Integer getPort() {
        return port;
    }

    public Boolean getSslEnabled() {
        return isSslEnabled;
    }

    public String getCertStorePassword() {
        return certStorePassword;
    }
}
