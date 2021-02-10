package com.yushkevich.metrics.commons.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResolverUtilsTest {

    @Test
    void testValidProperties() {
        var properties = ResolverUtils.resolveProperties(new String[]{"--propA=a", "--propB=B"});

        assertEquals("a", properties.get("propA"));
        assertEquals("B", properties.get("propB"));
    }

    @Test
    void testNotValidProperties() {
        assertNull(ResolverUtils.resolveProperties(new String[]{"propA:a", "-propA=a"}).get("propA"));
    }

    @Test
    void configResolutionFromProfile() {
        assertEquals("env/abc.yml", ResolverUtils.getConfigFileName("abc"));
    }
}
