package com.yushkevich.metrics.commons.utils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ResolverUtils {

    private static final String PROPERTY_PREFIX = "--";
    private static final String PROPERTY_SEPARATOR = "=";

    private ResolverUtils() {
    }

    public static Map<String, String> resolveProperties(String[] args) {
        return Stream.of(args)
                .filter(a -> a.startsWith(PROPERTY_PREFIX))
                .map(a -> a.replace(PROPERTY_PREFIX, "").split(PROPERTY_SEPARATOR))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    public static String getConfigFileName(String profile) {
        return String.format("env/%s.yml", profile);
    }
}
