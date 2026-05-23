package com.vachana.qa.pages;

import com.vachana.qa.config.ConfigManager;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    private final By logo = By.cssSelector("img[alt='Website for automation practice']");
    private final By productSection = By.cssSelector(".features_items");
    private final By signupLoginLink = By.cssSelector("a[href='/login']");
    private final By productsLink = By.cssSelector("a[href='/products']");
    private final By cartLink = By.cssSelector("a[href='/view_cart']");
    private final By contactUsLink = By.cssSelector("a[href='/contact_us']");
    private final By testCasesLink = By.cssSelector("a[href='/test_cases']");
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By deleteAccountLink = By.cssSelector("a[href='/delete_account']");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");
    private final By subscriptionEmail = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By subscriptionSuccess = By.cssSelector("#success-subscribe .alert-success");
    private final By categorySidebar = By.id("accordian");

    public void open() {
        driver.get(ConfigManager.get("base.url"));
        waits.pageLoaded();
    }

    public boolean isHomeVisible() {
        return elements.isDisplayed(logo) && elements.isDisplayed(productSection);
    }

    public void goToSignupLogin() {
        openPath("/login");
    }

    public void goToProducts() {
        openPath("/products");
    }

    public void goToCart() {
        openPath("/view_cart");
    }

    public void goToContactUs() {
        openPath("/contact_us");
    }

    public void goToTestCases() {
        openPath("/test_cases");
    }

    public boolean isLoggedInAs(String customerName) {
        return elements.isDisplayed(By.xpath("//a[contains(normalize-space(),'Logged in as') and contains(normalize-space(),\"" + customerName + "\")]"));
    }

    public void logout() {
        elements.click(logoutLink);
    }

    public void deleteAccount() {
        elements.click(deleteAccountLink);
    }

    public void continueAfterAccountAction() {
        elements.click(continueButton);
    }

    public void subscribeWith(String email) {
        elements.scrollIntoView(subscriptionEmail);
        elements.type(subscriptionEmail, email);
        elements.click(subscribeButton);
    }

    public boolean subscriptionSuccessVisible() {
        return elements.isDisplayed(subscriptionSuccess);
    }

    public boolean categorySidebarVisible() {
        return elements.isDisplayed(categorySidebar);
    }
}
