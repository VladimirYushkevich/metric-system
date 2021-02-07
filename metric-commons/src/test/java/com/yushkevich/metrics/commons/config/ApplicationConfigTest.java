package com.yushkevich.metrics.commons.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationConfigTest {

    private ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidApplicationConfig() {
        var appConfig = configParser.readProperties("yml/valid_app_config.yml",
                ApplicationConfig.class);

        assertEquals("topic", appConfig.kafkaProperties.topic);
        assertEquals("url", appConfig.kafkaProperties.serverUrl);
        assertEquals(123, appConfig.kafkaProperties.port);
    }

    @Test
    void failOnNotValidKafkaConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("yml/not_valid_app_config.yml",
                ApplicationConfig.class));
    }
}
