package com.yushkevich.metrics.commons.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationConfigTest {

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidApplicationConfig() {
        var appConfig = configParser.readProperties("yml/valid_config.yml",
                ApplicationConfig.class);

        assertEquals("topic", appConfig.getKafkaProperties().getTopic());
        assertEquals("url", appConfig.getKafkaProperties().getServerUrl());
        assertEquals(123, appConfig.getKafkaProperties().getPort());
    }

    @Test
    void failOnNotValidKafkaConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("yml/not_valid_config.yml",
                ApplicationConfig.class));
    }
}
