package com.yushkevich.metrics.producer;

import com.yushkevich.metrics.commons.config.ConfigParser;
import com.yushkevich.metrics.commons.utils.ResolverUtils;
import com.yushkevich.metrics.producer.config.ProducerConfig;
import com.yushkevich.metrics.producer.message.MetricProducer;
import com.yushkevich.metrics.producer.service.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MetricProducerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricProducer.class);

    public static void main(String[] args) {
        var commandLineArguments = ResolverUtils.resolveProperties(args);
        var profile = commandLineArguments.getOrDefault("profile", "local");
        var configFileName = ResolverUtils.getConfigFileName(profile);
        LOGGER.info("Resolved '{}' profile, properties taken from '{}' file", profile, configFileName);

        var producerConfig = new ConfigParser().readProperties(configFileName, ProducerConfig.class);
        var reporter = new Reporter();
        var executor = Executors.newScheduledThreadPool(1);
        var producerTask = new MetricProducer(producerConfig.getKafkaProperties(), reporter);
        executor.scheduleAtFixedRate(producerTask, 0, producerConfig.getReporterProperties().getPollInterval(),
                TimeUnit.SECONDS);
    }
}
