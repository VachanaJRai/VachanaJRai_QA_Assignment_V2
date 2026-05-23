package com.vachana.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class LoginSignupPage extends BasePage {
    private final By loginHeader = By.xpath("//h2[normalize-space()='Login to your account']");
    private final By signupHeader = By.xpath("//h2[normalize-space()='New User Signup!']");
    private final By loginEmail = By.cssSelector("[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("[data-qa='login-password']");
    private final By loginButton = By.cssSelector("[data-qa='login-button']");
    private final By signupName = By.cssSelector("[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By invalidLoginMessage = By.xpath("//p[normalize-space()='Your email or password is incorrect!']");
    private final By existingEmailMessage = By.xpath("//p[normalize-space()='Email Address already exist!']");

    public boolean isLoaded() {
        return elements.isDisplayed(loginHeader) && elements.isDisplayed(signupHeader);
    }

    public void login(String email, String password) {
        elements.type(loginEmail, email);
        elements.type(loginPassword, password);
        elements.click(loginButton);
    }

    public void startSignup(String name, String email) {
        elements.type(signupName, name);
        elements.type(signupEmail, email);
        elements.click(signupButton);
    }

    public boolean invalidLoginMessageVisible() {
        return elements.isDisplayed(invalidLoginMessage) || hasNativeLoginValidation();
    }

    public boolean existingEmailMessageVisible() {
        return elements.isDisplayed(existingEmailMessage);
    }

    private boolean hasNativeLoginValidation() {
        Object invalidFieldCount = ((JavascriptExecutor) driver).executeScript("""
                return Array.from(document.querySelectorAll("[data-qa='login-email'], [data-qa='login-password']"))
                    .filter(element => !element.validity.valid).length;
                """);
        return Long.parseLong(invalidFieldCount.toString()) > 0;
    }
}