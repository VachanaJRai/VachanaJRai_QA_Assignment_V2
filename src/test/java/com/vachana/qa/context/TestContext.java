package com.vachana.qa.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TestContext {
    private static final ThreadLocal<Map<String, Object>> STORE = ThreadLocal.withInitial(HashMap::new);

    private TestContext() {
    }

    public static void put(String key, Object value) {
        STORE.get().put(key, value);
    }

    public static Object get(String key) {
        return STORE.get().get(key);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = STORE.get().get(key);
        if (value == null) {
            throw new IllegalStateException("No value stored in test context for key: " + key);
        }
        return type.cast(value);
    }

    public static <T> Optional<T> find(String key, Class<T> type) {
        Object value = STORE.get().get(key);
        return value == null ? Optional.empty() : Optional.of(type.cast(value));
    }

    public static void remove(String key) {
        STORE.get().remove(key);
    }

    public static void clear() {
        STORE.get().clear();
        STORE.remove();
    }
}
