package com.vachana.qa.utils;

import com.vachana.qa.config.ConfigManager;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

public final class DownloadUtils {
    private static final String PARTIAL_CHROME_DOWNLOAD = ".crdownload";
    private static final String PARTIAL_TEMP_DOWNLOAD = ".tmp";

    private DownloadUtils() {
    }

    public static void clearDownloadDirectory() {
        Path downloadDirectory = downloadDirectory();
        try {
            Files.createDirectories(downloadDirectory);
            try (Stream<Path> files = Files.list(downloadDirectory)) {
                files.filter(Files::isRegularFile).forEach(DownloadUtils::deleteQuietly);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to prepare download directory: " + downloadDirectory, exception);
        }
    }

    public static Path waitForDownloadContaining(String fileNamePart) {
        Path downloadDirectory = downloadDirectory();
        Wait<Path> wait = new FluentWait<>(downloadDirectory)
                .withTimeout(Duration.ofSeconds(ConfigManager.getLong("timeout.seconds", 15)))
                .pollingEvery(Duration.ofMillis(ConfigManager.getLong("polling.millis", 250)));

        return wait.until(directory -> findCompletedDownload(directory, fileNamePart).orElse(null));
    }

    private static Path downloadDirectory() {
        return ConfigManager.getPath("downloads.dir", "target/downloads").toAbsolutePath();
    }

    private static Optional<Path> findCompletedDownload(Path directory, String fileNamePart) {
        String normalizedNamePart = fileNamePart.toLowerCase();
        try (Stream<Path> files = Files.list(directory)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().contains(normalizedNamePart))
                    .filter(path -> !path.getFileName().toString().endsWith(PARTIAL_CHROME_DOWNLOAD))
                    .filter(path -> !path.getFileName().toString().endsWith(PARTIAL_TEMP_DOWNLOAD))
                    .filter(DownloadUtils::hasContent)
                    .findFirst();
        } catch (IOException exception) {
            return Optional.empty();
        }
    }

    private static boolean hasContent(Path path) {
        try {
            return Files.size(path) > 0;
        } catch (IOException exception) {
            return false;
        }
    }

    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // A previous browser session may still be releasing the file; the download wait uses unique invoice names.
        }
    }
}
