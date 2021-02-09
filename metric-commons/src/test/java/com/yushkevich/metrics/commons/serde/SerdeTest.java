package com.yushkevich.metrics.commons.serde;

import com.yushkevich.metrics.commons.message.OSMetric;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerdeTest {

    private final MetricJsonSerializer serializer = new MetricJsonSerializer();
    private final MetricJsonDeserializer deserializer = new MetricJsonDeserializer();
    private OSMetric metric;

    @BeforeEach
    void setUp() {
        metric = OSMetric.newBuilder()
                .withDescription("description")
                .withName("name")
                .withValue(1.0d)
                .withCreatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testConsistency() {
        var serialized1 = serializer.serialize("topic", metric);
        var serialized2 = serializer.serialize("topic", metric);

        var metric1 = deserializer.deserialize("topic", serialized1);
        var metric2 = deserializer.deserialize("topic", serialized2);

        assertEquals(metric1, metric2);
        assertEquals(metric, metric1);
        assertEquals(metric, metric2);
    }
}
