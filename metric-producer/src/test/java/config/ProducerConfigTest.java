package config;

import com.yushkevich.metric.producer.config.ProducerConfig;
import com.yushkevich.metrics.commons.config.ConfigParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerConfigTest {

    private final ConfigParser configParser = new ConfigParser();

    @Test
    void parseValidProducerConfig() {
        var producerConfig = configParser.readProperties("yml/valid_config.yml",
                ProducerConfig.class);

        assertEquals(3, producerConfig.getReporterProperties().getPollInterval());
    }

    @Test
    void failOnNotValidReporterConfig() {
        assertThrows(IllegalArgumentException.class, () -> configParser.readProperties("yml/not_valid_config.yml",
                ProducerConfig.class));
    }
}
