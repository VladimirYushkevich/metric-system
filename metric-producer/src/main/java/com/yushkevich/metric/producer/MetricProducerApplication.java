package com.yushkevich.metric.producer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MetricProducerApplication {
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Producer producerThread = new Producer(KafkaProperties.TOPIC, 100, latch);
        producerThread.start();

        if (!latch.await(1, TimeUnit.MINUTES)) {
            throw new TimeoutException("Timeout after 5 minutes waiting for metric producer");
        }

        System.out.println("All finished!");
    }
}
