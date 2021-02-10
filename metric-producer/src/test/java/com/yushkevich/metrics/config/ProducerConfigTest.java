package com.yushkevich.metrics.config;

import com.yushkevich.metrics.commons.config.ConfigParser;
import com.yushkevich.metrics.producer.config.ProducerConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerConfigTest {

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidProducerConfig() {
        var producerConfig = configParser.readProperties("config/valid_config.yml",
                ProducerConfig.class);

        assertEquals(3, producerConfig.getReporterProperties().getPollInterval());
        assertEquals("env", producerConfig.getReporterProperties().getEnv());
    }

    @Test
    void failOnNotValidReporterConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("config/not_valid_config.yml",
                ProducerConfig.class));
    }
}
