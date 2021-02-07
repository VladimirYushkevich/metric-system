package com.yushkevich.metric.producer;

import com.yushkevich.metrics.commons.config.KafkaProperties;
import com.yushkevich.metrics.commons.message.OSMetric;
import com.yushkevich.metrics.commons.serde.MetricJsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Producer extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());

    private final KafkaProducer<Integer, OSMetric> producer;
    private final String topic;
    private final Reporter reporter;

    public Producer(final KafkaProperties kafkaProperties, final Reporter reporter) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.serverUrl + ":" + kafkaProperties.port);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MetricProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MetricJsonSerializer.class.getName());

        producer = new KafkaProducer<>(props);
        this.topic = kafkaProperties.topic;

        this.reporter = reporter;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            // Send synchronously
            try {
                reporter.getMetrics()
                        .forEach(this::send);

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(OSMetric metric) {
        try {
            producer.send(new ProducerRecord<>(topic, metric)).get();

            LOGGER.info("Sent message: (" + metric + ")");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}