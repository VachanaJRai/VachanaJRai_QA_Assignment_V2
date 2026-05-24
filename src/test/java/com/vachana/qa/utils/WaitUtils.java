package com.vachana.qa.utils;

import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtils {
    private final WebDriver driver;

    public WaitUtils() {
        this.driver = DriverFactory.getDriver();
    }

    public WebElement visible(By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickable(By locator) {
        return driverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public List<WebElement> visibleElements(By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public boolean invisible(By locator) {
        return driverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean textPresent(By locator, String text) {
        return driverWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public void pageLoaded() {
        driverWait().until(webDriver -> "complete".equals(
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
    }

    public <T> T until(Function<WebDriver, T> condition) {
        return driverWait().until(condition);
    }

    private WebDriverWait driverWait() {
        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigManager.getLong("timeout.seconds", 15)),
                Duration.ofMillis(ConfigManager.getLong("polling.millis", 250))
        );
        wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class);
        return wait;
    }
}
