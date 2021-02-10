package com.yushkevich.metric.consumer.config;

import com.yushkevich.metrics.commons.config.ConfigParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsumerConfigTest {

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidConsumerConfig() {
        var consumerConfig = configParser.readProperties("yml/valid_config.yml",
                ConsumerConfig.class);

        assertEquals("driver-class", consumerConfig.getPersistenceProperties().getDriver());
        assertEquals("jdbc-url", consumerConfig.getPersistenceProperties().getJdbcUrl());
        assertEquals("user", consumerConfig.getPersistenceProperties().getUser());
        assertEquals("password", consumerConfig.getPersistenceProperties().getPassword());
    }

    @Test
    void failOnNotValidPersistenceConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("yml/not_valid_config.yml",
                ConsumerConfig.class));
    }
}