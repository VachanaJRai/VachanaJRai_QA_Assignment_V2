package com.vachana.qa.driver;

import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.utils.LoggerUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.PageLoadStrategy;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {
    private static final Logger LOGGER = LoggerUtils.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver initializeDriver() {
        if (DRIVER.get() != null) {
            return DRIVER.get();
        }

        BrowserType browser = BrowserType.from(ConfigManager.get("browser", "chrome"));
        boolean headless = ConfigManager.getBoolean("headless", false);
        WebDriver driver = switch (browser) {
            case CHROME -> new ChromeDriver(chromeOptions(headless));
            case FIREFOX -> new FirefoxDriver(firefoxOptions(headless));
            case EDGE -> new EdgeDriver(edgeOptions(headless));
        };

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigManager.getLong("page.load.timeout.seconds", 45)));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        if (ConfigManager.getBoolean("browser.window.maximize", true) && !headless) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
        LOGGER.info("Initialized {} browser. headless={}", browser, headless);
        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for the current thread.");
        }
        return driver;
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            LOGGER.info("Closed browser for current thread");
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-background-networking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-sync");
        options.addArguments("--dns-prefetch-disable");
        options.setExperimentalOption("prefs", downloadPreferences());
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setAcceptInsecureCerts(true);
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-background-networking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-sync");
        options.addArguments("--dns-prefetch-disable");
        options.setExperimentalOption("prefs", downloadPreferences());
        return options;
    }

    private static Map<String, Object> downloadPreferences() {
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("download.default_directory", ConfigManager.getPath("downloads.dir", "target/downloads").toAbsolutePath().toString());
        preferences.put("download.prompt_for_download", false);
        preferences.put("profile.default_content_setting_values.notifications", 2);
        preferences.put("profile.managed_default_content_settings.ads", 2);
        return preferences;
    }
}
