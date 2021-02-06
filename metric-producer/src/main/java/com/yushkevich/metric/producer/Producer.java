package com.yushkevich.metric.producer;

import com.yushkevich.metrics.commons.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer extends Thread {

    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final int numRecords;

    public Producer(final String topic,
                    final int numRecords) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MetricProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.numRecords = numRecords;
    }

    @Override
    public void run() {
        int messageKey = 0;
        int recordsSent = 0;
        while (recordsSent < numRecords) {
            String messageStr = "Message_" + messageKey;
            // Send synchronously
            try {
                producer.send(new ProducerRecord<>(topic,
                        messageKey,
                        messageStr)).get();
                System.out.println("Sent message: (" + messageKey + ", " + messageStr + ")");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            messageKey += 2;
            recordsSent += 1;
        }
        System.out.println("Producer sent " + numRecords + " records successfully");
    }
}