package com.yushkevich.metrics.consumer;

import com.yushkevich.metrics.commons.config.ConfigParser;
import com.yushkevich.metrics.commons.utils.ResolverUtils;
import com.yushkevich.metrics.consumer.config.ConsumerConfig;
import com.yushkevich.metrics.consumer.message.MetricConsumer;
import com.yushkevich.metrics.consumer.repository.C3poDataSource;
import com.yushkevich.metrics.consumer.repository.MetricRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricConsumerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricConsumerApplication.class);

    public static void main(String[] args) {
        var commandLineArguments = ResolverUtils.resolveProperties(args);
        var profile = commandLineArguments.getOrDefault("profile", "local");
        var configFileName = ResolverUtils.getConfigFileName(profile);
        LOGGER.info("Resolved '{}' profile, properties taken from '{}' file", profile, configFileName);

        var consumerConfig = new ConfigParser().readProperties(configFileName, ConsumerConfig.class);
        var dataSource = new C3poDataSource(consumerConfig.getPersistenceProperties());
        var metricRegistry = new MetricRepository(dataSource);
        var consumerThread = new MetricConsumer(consumerConfig.getKafkaProperties(), "MetricConsumer",
                false, metricRegistry);
        consumerThread.start();
    }
}
