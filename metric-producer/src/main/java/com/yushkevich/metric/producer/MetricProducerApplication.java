package com.yushkevich.metric.producer;

import com.yushkevich.metrics.commons.properties.ApplicationConfig;
import com.yushkevich.metrics.commons.properties.ConfigParser;

public class MetricProducerApplication {
    public static void main(String[] args) {
        var appConfig = new ConfigParser().readProperties("config.yml",
                ApplicationConfig.class);
        var producerThread = new Producer(appConfig.kafkaProperties, 100);
        producerThread.start();
    }
}
