package com.yushkevich.metrics.commons.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ConfigParser {
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    public ConfigParser() {
        MAPPER.findAndRegisterModules();
    }

    public <T> T readProperties(String filePath, Class<T> clazz) {
        try {
            return MAPPER.readValue(ConfigParser.class.getClassLoader().getResourceAsStream(filePath), clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("Can't read properties from `%s` file, to map %s", filePath, clazz.getSimpleName()), e);
        }
    }
}
