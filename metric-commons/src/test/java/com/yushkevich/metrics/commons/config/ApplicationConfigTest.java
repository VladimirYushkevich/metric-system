package com.yushkevich.metrics.commons.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationConfigTest {

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidApplicationConfig() {
        var appConfig = configParser.readProperties("config/valid_config.yml",
                ApplicationConfig.class);

        var kafkaProperties = appConfig.getKafkaProperties();

        assertEquals("topic", kafkaProperties.getTopic());
        assertEquals("url", kafkaProperties.getServerUrl());
        assertEquals(123, kafkaProperties.getPort());
        assertFalse(kafkaProperties.getSslEnabled());
        assertEquals("pass", kafkaProperties.getCertStorePassword());
    }

    @Test
    void failOnNotValidKafkaConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("config/not_valid_config.yml",
                ApplicationConfig.class));
    }
}
