package com.yushkevich.metric.producer;

import com.yushkevich.metrics.commons.KafkaProperties;

import java.util.concurrent.TimeoutException;

public class MetricProducerApplication {
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Producer producerThread = new Producer(KafkaProperties.TOPIC, 100);
        producerThread.start();
    }
}
