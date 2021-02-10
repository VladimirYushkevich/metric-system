package com.yushkevich.metrics.producer.message;

import com.yushkevich.metrics.commons.config.KafkaProperties;
import com.yushkevich.metrics.commons.message.OSMetric;
import com.yushkevich.metrics.commons.serde.MetricJsonSerializer;
import com.yushkevich.metrics.producer.service.Reporter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MetricProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricProducer.class);

    private final KafkaProducer<Integer, OSMetric> producer;
    private final String topic;
    private final Reporter reporter;

    public MetricProducer(final KafkaProperties kafkaProperties, final Reporter reporter) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServerUrl() + ":" + kafkaProperties.getPort());
        if (kafkaProperties.getSslEnabled()) {
            props.put("security.protocol", "SSL");
            props.put("ssl.endpoint.identification.algorithm", "");
            props.put("ssl.truststore.location", Objects.requireNonNull(
                    MetricProducer.class.getClassLoader().getResource("certstore/client.truststore.jks")).getPath());
            props.put("ssl.truststore.password", kafkaProperties.getCertStorePassword());
            props.put("ssl.keystore.type", "PKCS12");
            props.put("ssl.keystore.location", Objects.requireNonNull(
                    MetricProducer.class.getClassLoader().getResource("certstore/client.keystore.p12")).getPath());
            props.put("ssl.keystore.password", kafkaProperties.getCertStorePassword());
            props.put("ssl.key.password", kafkaProperties.getCertStorePassword());
        }
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MetricProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MetricJsonSerializer.class.getName());

        producer = new KafkaProducer<>(props);
        this.topic = kafkaProperties.getTopic();

        this.reporter = reporter;
    }

    @Override
    public void run() {
        reporter.getMetrics().forEach(this::send);
    }

    private void send(OSMetric metric) {
        try {
            producer.send(new ProducerRecord<>(topic, metric)).get();

            LOGGER.info("Sent message: {}", metric);
        } catch (InterruptedException | ExecutionException e) {
            producer.close();
            LOGGER.error("Failed to send {} message", metric, e);
        }
    }
}