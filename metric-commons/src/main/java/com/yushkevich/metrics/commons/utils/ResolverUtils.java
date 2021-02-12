package com.yushkevich.metrics.commons.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    public static String resolveFilePathInJar(String fromJarFileName) {
        try (var inputStream = ResolverUtils.class.getClassLoader().getResourceAsStream(fromJarFileName)) {
            var somethingFile = File.createTempFile(fromJarFileName, ".txt");
            if (inputStream != null) {
                Files.copy(inputStream, somethingFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return somethingFile.getPath();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't read content from jar: " + fromJarFileName, e);
        }
    }
}
