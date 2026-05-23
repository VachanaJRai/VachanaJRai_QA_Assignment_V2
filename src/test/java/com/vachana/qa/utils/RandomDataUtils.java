package com.vachana.qa.utils;

import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.models.Customer;

import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomDataUtils {
    private RandomDataUtils() {
    }

    public static String uniqueEmail() {
        return "qa_" + Instant.now().toEpochMilli() + "_" + ThreadLocalRandom.current().nextInt(1000, 9999)
                + "@example.invalid";
    }

    public static String uniqueName(String prefix) {
        return prefix + " " + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    public static Customer uniqueCustomer() {
        String suffix = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
        return new Customer(
                "QA User " + suffix,
                uniqueEmail().toLowerCase(Locale.ROOT),
                ConfigManager.get("default.password"),
                "QA",
                "User" + suffix,
                "Automation Exercise Labs",
                "123 Test Street",
                "Suite " + suffix,
                "India",
                "Karnataka",
                "Bengaluru",
                "560001",
                "98765" + suffix
        );
    }
}
