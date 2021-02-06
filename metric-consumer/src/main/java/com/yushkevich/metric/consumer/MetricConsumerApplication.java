package com.yushkevich.metric.consumer;

import com.yushkevich.metrics.commons.KafkaProperties;

import java.util.Optional;

public class MetricConsumerApplication {
    public static void main(String[] args) {
        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC, "MetricConsumer", Optional.empty(),
                false, 10);
        consumerThread.start();
    }
}
