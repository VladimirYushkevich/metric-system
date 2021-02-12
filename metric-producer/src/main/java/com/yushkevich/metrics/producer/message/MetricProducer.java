package com.yushkevich.metrics.producer.message;

import com.yushkevich.metrics.commons.config.KafkaProperties;
import com.yushkevich.metrics.commons.message.Metric;
import com.yushkevich.metrics.commons.utils.ResolverUtils;
import com.yushkevich.metrics.producer.service.Reporter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MetricProducer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricProducer.class);

    private final KafkaProducer<Integer, Metric> producer;
    private final String topic;
    private final Reporter reporter;

    public MetricProducer(final KafkaProperties kafkaProperties, final Reporter reporter) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServerUrl() + ":" + kafkaProperties.getPort());
//        props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
        props.put("schema.registry.url", "https://kafka-1606798a-yushkevich-c582.aivencloud.com:12857");
        if (kafkaProperties.getSslEnabled()) {
            props.put("security.protocol", "SSL");
            props.put("ssl.endpoint.identification.algorithm", "");
            props.put("ssl.truststore.location", ResolverUtils.resolveFilePathInJar("certstore/client.truststore.jks"));
            props.put("ssl.truststore.password", kafkaProperties.getCertStorePassword());
            props.put("ssl.keystore.type", "PKCS12");
            props.put("ssl.keystore.location",
                    ResolverUtils.resolveFilePathInJar("certstore/client.keystore.p12"));
            props.put("ssl.keystore.password", kafkaProperties.getCertStorePassword());
            props.put("ssl.key.password", kafkaProperties.getCertStorePassword());
            props.put("basic.auth.credentials.source", "USER_INFO");
            props.put("basic.auth.user.info", "avnadmin:l87t9r7seq9xverv");
        }
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "MetricProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");

        producer = new KafkaProducer<>(props);
        this.topic = kafkaProperties.getTopic();

        this.reporter = reporter;
    }

    @Override
    public void run() {
        reporter.getMetrics().forEach(this::send);
    }

    private void send(Metric metric) {
        try {
            producer.send(new ProducerRecord<>(topic, metric)).get();

            LOGGER.info("Sent message: {}", metric);
        } catch (InterruptedException | ExecutionException e) {
            producer.close();
            LOGGER.error("Failed to send {} message", metric, e);
        }
    }
}