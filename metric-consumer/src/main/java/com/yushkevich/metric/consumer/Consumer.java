package com.yushkevich.metric.consumer;

import com.yushkevich.metrics.commons.config.KafkaProperties;
import com.yushkevich.metrics.commons.message.OSMetric;
import com.yushkevich.metrics.commons.serde.MetricJsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

public class Consumer extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());

    private final KafkaConsumer<Integer, OSMetric> consumer;
    private final String topic;

    public Consumer(final KafkaProperties kafkaProperties,
                    final String groupId,
                    final boolean readCommitted) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.serverUrl + ":" + kafkaProperties.port);
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
        this.topic = kafkaProperties.topic;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        consumer.subscribe(Collections.singletonList(this.topic));
        while (true) {
            ConsumerRecords<Integer, OSMetric> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<Integer, OSMetric> record : records) {
                LOGGER.info(String.format("offset = %d, key = %s, value = %s",
                        record.offset(), record.key(), record.value()));
            }
        }
    }

}
