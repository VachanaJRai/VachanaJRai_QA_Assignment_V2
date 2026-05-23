package com.vachana.qa.pages;

import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.driver.DriverFactory;
import com.vachana.qa.utils.ElementUtils;
import com.vachana.qa.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final ElementUtils elements;
    protected final WaitUtils waits;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.elements = new ElementUtils();
        this.waits = new WaitUtils();
    }

    protected void openPath(String path) {
        driver.get(ConfigManager.get("base.url") + path);

        try {
            waits.visible(By.tagName("body"));
        } catch (RuntimeException ignored) {
            // Public site has slow third-party scripts/ads; do not fail navigation only because readyState is noisy.
        }
    }

    protected By containsText(String text) {
        return By.xpath("//*[contains(normalize-space(),\"" + text + "\")]");
    }

    protected By exactText(String text) {
        return By.xpath("//*[normalize-space()=\"" + text + "\"]");
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }
}