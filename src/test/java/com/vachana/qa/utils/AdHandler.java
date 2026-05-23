package com.vachana.qa.utils;

import com.vachana.qa.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdHandler {
    private static final List<By> POSSIBLE_CLOSE_BUTTONS = List.of(
            By.cssSelector("button[aria-label='Close']"),
            By.cssSelector("div[aria-label='Close ad']"),
            By.cssSelector("[id*='dismiss-button']"),
            By.cssSelector(".modal.show button.close")
    );

    private final WebDriver driver;

    public AdHandler() {
        this.driver = DriverFactory.getDriver();
    }

    public void dismissAdsAndOverlays() {
        clickKnownCloseButtons();
        neutralizeAdContainers();
        driver.switchTo().defaultContent();
    }

    private void clickKnownCloseButtons() {
        for (By closeButton : POSSIBLE_CLOSE_BUTTONS) {
            for (WebElement element : driver.findElements(closeButton)) {
                try {
                    if (element.isDisplayed() && element.isEnabled()) {
                        element.click();
                    }
                } catch (RuntimeException ignored) {
                    driver.switchTo().defaultContent();
                }
            }
        }
    }

    private void neutralizeAdContainers() {
        String script = """
                const selectors = [
                  "iframe[id^='aswift']",
                  "iframe[src*='googlesyndication']",
                  "iframe[src*='googleads']",
                  "ins.adsbygoogle",
                  "div[id^='google_ads']",
                  "div[aria-label='Advertisement']"
                ];
                selectors.forEach(selector => {
                  document.querySelectorAll(selector).forEach(element => {
                    element.style.pointerEvents = "none";
                    element.style.visibility = "hidden";
                  });
                });
                """;
        ((JavascriptExecutor) driver).executeScript(script);
    }
}
