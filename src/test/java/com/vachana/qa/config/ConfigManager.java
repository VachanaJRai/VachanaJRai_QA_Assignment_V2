package com.vachana.qa.config;

import com.vachana.qa.constants.FrameworkConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

public final class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties(FrameworkConstants.CONFIG_FILE, true);
        String environment = System.getProperty("env", PROPERTIES.getProperty("environment", "qa"));
        loadProperties("config/" + environment + ".properties", false);
    }

    private ConfigManager() {
    }

    public static String get(String key) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing required config key: " + key);
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, Boolean.toString(defaultValue)));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, Integer.toString(defaultValue)));
    }

    public static long getLong(String key, long defaultValue) {
        return Long.parseLong(get(key, Long.toString(defaultValue)));
    }

    public static Path getPath(String key, String defaultValue) {
        return Path.of(get(key, defaultValue));
    }

    private static void loadProperties(String resourcePath, boolean required) {
        try (InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (stream == null) {
                if (required) {
                    throw new IllegalStateException("Unable to find required properties file: " + resourcePath);
                }
                return;
            }
            PROPERTIES.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load properties file: " + resourcePath, exception);
        }
    }

    public static String maskIfSensitive(String key, String value) {
        Objects.requireNonNull(key, "Config key must not be null");
        if (key.toLowerCase().contains("password") || key.toLowerCase().contains("secret")) {
            return "****";
        }
        return value;
    }
}
