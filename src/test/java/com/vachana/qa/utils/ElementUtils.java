package com.vachana.qa.utils;

import com.vachana.qa.driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementUtils {
    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final AdHandler adHandler;

    public ElementUtils() {
        this.driver = DriverFactory.getDriver();
        this.waitUtils = new WaitUtils();
        this.adHandler = new AdHandler();
    }

    public void click(By locator) {
        adHandler.dismissAdsAndOverlays();
        try {
            WebElement element = waitUtils.clickable(locator);
            scrollIntoView(element);
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException | TimeoutException exception) {
            adHandler.dismissAdsAndOverlays();
            WebElement element = waitUtils.visible(locator);
            jsClick(element);
        }
    }

    public void clickFirstDisplayed(By locator) {
        adHandler.dismissAdsAndOverlays();
        WebElement element = waitUtils.until(webDriver -> webDriver.findElements(locator)
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));

        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException | TimeoutException exception) {
            adHandler.dismissAdsAndOverlays();
            jsClick(element);
        }
    }

    public void type(By locator, String value) {
        WebElement element = waitUtils.visible(locator);
        scrollIntoView(element);
        if (!"file".equalsIgnoreCase(element.getDomAttribute("type"))) {
            element.clear();
        }
        element.sendKeys(value);
    }

    public String text(By locator) {
        return waitUtils.visible(locator).getText().trim();
    }

    public boolean isDisplayed(By locator) {
        try {
            return waitUtils.visible(locator).isDisplayed();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    public void selectByVisibleText(By locator, String visibleText) {
        new Select(waitUtils.visible(locator)).selectByVisibleText(visibleText);
    }

    public void scrollIntoView(By locator) {
        scrollIntoView(waitUtils.visible(locator));
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void acceptAlertIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored) {
            // Some browsers resolve the confirmation dialog before this hook observes it.
        }
    }

    public boolean hasAny(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
