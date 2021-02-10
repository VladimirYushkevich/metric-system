package com.yushkevich.metrics.consumer.message;

import com.yushkevich.metrics.commons.config.KafkaProperties;
import com.yushkevich.metrics.commons.message.OSMetric;
import com.yushkevich.metrics.commons.serde.MetricJsonDeserializer;
import com.yushkevich.metrics.consumer.repository.MetricRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final KafkaConsumer<Integer, OSMetric> consumer;
    private final String topic;
    private final MetricRepository metricRepository;

    public Consumer(final KafkaProperties kafkaProperties,
                    final String groupId,
                    final boolean readCommitted,
                    MetricRepository metricRepository) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServerUrl() + ":" + kafkaProperties.getPort());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MetricJsonDeserializer.class.getName());
        if (readCommitted) {
            props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        }
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        this.topic = kafkaProperties.getTopic();

        this.metricRepository = metricRepository;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        consumer.subscribe(Collections.singletonList(this.topic));
        while (true) {
            var records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<Integer, OSMetric> record : records) {
                var metric = record.value();
                try {
                    metricRepository.insert(metric);
                } catch (SQLException ex) {
                    // ignore failed insert and allow consumer to continue
                    LOGGER.warn("Can't insert in DB: {} ", metric, ex);
                }
            }
        }
    }
}
