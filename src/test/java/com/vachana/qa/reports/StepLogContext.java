package com.vachana.qa.reports;

import java.util.Optional;

public final class StepLogContext {
    private static final ThreadLocal<StepLog> CURRENT_STEP = new ThreadLocal<>();

    private StepLogContext() {
    }

    public static void start(String keyword, String text) {
        CURRENT_STEP.set(new StepLog(clean(keyword), clean(text), null, null));
    }

    public static void finish(String status, Throwable error) {
        StepLog current = CURRENT_STEP.get();
        if (current != null) {
            CURRENT_STEP.set(new StepLog(current.keyword(), current.text(), status, error));
        }
    }

    public static Optional<StepLog> currentStep() {
        return Optional.ofNullable(CURRENT_STEP.get());
    }

    public static String currentStepDisplayName(int fallbackStepNumber) {
        return currentStep()
                .map(StepLog::displayName)
                .orElse("Step " + fallbackStepNumber);
    }

    public static boolean currentStepIsValidation() {
        return currentStep()
                .map(step -> step.keyword().equalsIgnoreCase("Then"))
                .orElse(false);
    }

    public static void clear() {
        CURRENT_STEP.remove();
    }

    private static String clean(String value) {
        return value == null ? "" : value.replaceAll("\\s+", " ").trim();
    }

    public record StepLog(String keyword, String text, String status, Throwable error) {
        public String displayName() {
            return (keyword + " " + text).replaceAll("\\s+", " ").trim();
        }
    }
}
