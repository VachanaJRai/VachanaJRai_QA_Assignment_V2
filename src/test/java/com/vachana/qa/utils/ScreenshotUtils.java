package com.vachana.qa.utils;

import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static byte[] screenshotBytes() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static Path saveStepScreenshot(String scenarioName, int stepNumber) {
        return saveScenarioScreenshot(scenarioName, String.format("step_%03d", stepNumber));
    }

    public static Path saveStepScreenshot(String scenarioName, String stepName, int stepNumber) {
        return saveScenarioScreenshot(scenarioName, String.format("step_%03d_%s", stepNumber, safeName(stepName)));
    }

    public static Path saveScenarioScreenshot(String scenarioName, String filePrefix) {
        try {
            Path screenshotDir = ConfigManager.getPath("screenshot.dir", "target/screenshots")
                    .resolve(safeName(scenarioName));
            Files.createDirectories(screenshotDir);

            String fileName = safeName(filePrefix) + "_" + LocalDateTime.now().format(FORMATTER) + ".png";
            Path destination = screenshotDir.resolve(fileName);

            Files.write(destination, screenshotBytes());
            return destination;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to save screenshot", exception);
        }
    }

    private static String safeName(String value) {
        String safeValue = value.replaceAll("[^a-zA-Z0-9._-]", "_");
        return safeValue.length() > 90 ? safeValue.substring(0, 90) : safeValue;
    }
}
