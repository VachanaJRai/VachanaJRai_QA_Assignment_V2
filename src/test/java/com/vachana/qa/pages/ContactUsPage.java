package com.vachana.qa.pages;

import com.vachana.qa.models.Customer;
import org.openqa.selenium.By;

import java.nio.file.Path;

public class ContactUsPage extends BasePage {
    private final By getInTouchHeader = By.xpath("//h2[normalize-space()='Get In Touch']");
    private final By name = By.cssSelector("[data-qa='name']");
    private final By email = By.cssSelector("[data-qa='email']");
    private final By subject = By.cssSelector("[data-qa='subject']");
    private final By message = By.cssSelector("[data-qa='message']");
    private final By uploadFile = By.name("upload_file");
    private final By submitButton = By.cssSelector("[data-qa='submit-button']");
    private final By successMessage = By.cssSelector(".status.alert-success");
    private final By homeButton = By.cssSelector("#form-section a[href='/']");

    public boolean isLoaded() {
        return elements.isDisplayed(getInTouchHeader);
    }

    public void submitMessage(Customer customer, String subjectText, String messageText, Path uploadPath) {
        elements.type(name, customer.fullName());
        elements.type(email, customer.email());
        elements.type(subject, subjectText);
        elements.type(message, messageText);
        elements.type(uploadFile, uploadPath.toAbsolutePath().toString());
        elements.click(submitButton);
        elements.acceptAlertIfPresent();
    }

    public boolean successVisible() {
        return elements.isDisplayed(successMessage);
    }

    public void goHomeFromSuccess() {
        elements.click(homeButton);
    }
}
