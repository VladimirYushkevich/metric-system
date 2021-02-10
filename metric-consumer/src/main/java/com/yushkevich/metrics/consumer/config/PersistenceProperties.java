package com.yushkevich.metrics.consumer.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersistenceProperties {
    @JsonProperty("driver-class")
    private String driver;
    @JsonProperty("jdbc-url")
    private String jdbcUrl;
    private String user;
    private String password;

    public String getDriver() {
        return driver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
