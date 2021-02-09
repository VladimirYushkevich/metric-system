package com.yushkevich.metric.producer;

import com.yushkevich.metric.producer.config.ProducerConfig;
import com.yushkevich.metric.producer.message.Producer;
import com.yushkevich.metric.producer.service.Reporter;
import com.yushkevich.metrics.commons.config.ConfigParser;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MetricProducerApplication {
    public static void main(String[] args) {
        var producerConfig = new ConfigParser().readProperties("config.yml", ProducerConfig.class);
        var reporter = new Reporter();
        var executor = Executors.newScheduledThreadPool(1);
        var producerTask = new Producer(producerConfig.getKafkaProperties(), reporter);
        executor.scheduleAtFixedRate(producerTask, 0, producerConfig.getReporterProperties().getPollInterval(),
                TimeUnit.SECONDS);
    }
}
