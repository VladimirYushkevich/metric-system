package com.yushkevich.metrics.commons.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yushkevich.metrics.commons.message.OSMetric;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MetricJsonDeserializer implements Deserializer<OSMetric> {
    private final ObjectMapper objectMapper;

    public MetricJsonDeserializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Override
    public OSMetric deserialize(String topic, final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, OSMetric.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing JSON message", e);
        }
    }
}
