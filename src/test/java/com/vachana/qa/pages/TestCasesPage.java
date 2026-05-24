package com.vachana.qa.pages;

import org.openqa.selenium.By;

public class TestCasesPage extends BasePage {
    private final By testCasesHeading = By.cssSelector("h2.title.text-center");

    public boolean isLoaded() {
        return currentUrl().contains("/test_cases")
                && elements.isDisplayed(testCasesHeading)
                && elements.text(testCasesHeading).contains("TEST CASES");
    }
}
