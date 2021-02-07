package com.yushkevich.metric.consumer;

import com.yushkevich.metrics.commons.config.ApplicationConfig;
import com.yushkevich.metrics.commons.config.ConfigParser;

public class MetricConsumerApplication {
    public static void main(String[] args) {
        var appConfig = new ConfigParser().readProperties("config.yml", ApplicationConfig.class);
        var consumerThread = new Consumer(appConfig.kafkaProperties, "MetricConsumer", false);
        consumerThread.start();
    }
}
