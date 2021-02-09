package com.yushkevich.metric.consumer;

import com.yushkevich.metric.consumer.config.ConsumerConfig;
import com.yushkevich.metric.consumer.message.Consumer;
import com.yushkevich.metric.consumer.repository.C3poDataSource;
import com.yushkevich.metric.consumer.repository.MetricRepository;
import com.yushkevich.metrics.commons.config.ConfigParser;

public class MetricConsumerApplication {
    public static void main(String[] args) {
        var consumerConfig = new ConfigParser().readProperties("config.yml", ConsumerConfig.class);
        var dataSource = new C3poDataSource(consumerConfig.getPersistenceProperties());
        var metricRegistry = new MetricRepository(dataSource);
        var consumerThread = new Consumer(consumerConfig.getKafkaProperties(), "MetricConsumer", false, metricRegistry);
        consumerThread.start();
    }
}
