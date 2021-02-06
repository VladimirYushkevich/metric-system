package com.yushkevich.metric.consumer;

import com.yushkevich.metrics.commons.properties.ApplicationConfig;
import com.yushkevich.metrics.commons.properties.ConfigParser;

import java.util.Optional;

public class MetricConsumerApplication {
    public static void main(String[] args) {
        var appConfig = new ConfigParser().readProperties("config.yml",
                ApplicationConfig.class);
        var consumerThread = new Consumer(appConfig.kafkaProperties, "MetricConsumer", Optional.empty(),
                false, 10);
        consumerThread.start();
    }
}
