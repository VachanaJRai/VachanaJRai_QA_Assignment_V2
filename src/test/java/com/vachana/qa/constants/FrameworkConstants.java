package com.vachana.qa.constants;

import java.nio.file.Path;

public final class FrameworkConstants {
    public static final String CONFIG_FILE = "config/config.properties";
    public static final String USER_TEST_DATA = "testdata/users.json";
    public static final String PRODUCTS_SCHEMA = "schemas/products-list-schema.json";
    public static final String BRANDS_SCHEMA = "schemas/brands-list-schema.json";
    public static final String MESSAGE_SCHEMA = "schemas/message-response-schema.json";

    public static final Path TARGET_DIR = Path.of("target");
    public static final Path CUCUMBER_REPORT_DIR = TARGET_DIR.resolve("cucumber-reports");
    public static final Path EXTENT_REPORT_DIR = TARGET_DIR.resolve("extent-reports");

    private FrameworkConstants() {
    }
}
