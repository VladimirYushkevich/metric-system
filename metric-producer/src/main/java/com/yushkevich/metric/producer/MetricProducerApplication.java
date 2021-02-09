package com.yushkevich.metric.producer;

import com.yushkevich.metric.producer.message.Producer;
import com.yushkevich.metric.producer.service.Reporter;
import com.yushkevich.metrics.commons.config.ApplicationConfig;
import com.yushkevich.metrics.commons.config.ConfigParser;

public class MetricProducerApplication {
    public static void main(String[] args) {
        var appConfig = new ConfigParser().readProperties("config.yml", ApplicationConfig.class);
        var reporter = new Reporter();
        var producerThread = new Producer(appConfig.getKafkaProperties(), reporter);
        producerThread.start();
    }
}
