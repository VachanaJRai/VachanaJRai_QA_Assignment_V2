package com.vachana.qa.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class TestDataReader {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private TestDataReader() {
    }

    public static Map<String, Object> readJsonAsMap(String resourcePath) {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalArgumentException("Test data file not found on classpath: " + resourcePath);
            }
            return OBJECT_MAPPER.readValue(stream, new TypeReference<>() {
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test data from: " + resourcePath, exception);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> section(String resourcePath, String sectionName) {
        Object value = readJsonAsMap(resourcePath).get(sectionName);
        if (value == null) {
            throw new IllegalArgumentException("Missing test data section: " + sectionName);
        }
        return (Map<String, Object>) value;
    }
}
