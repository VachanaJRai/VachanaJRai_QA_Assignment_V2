package com.vachana.qa.pages;

import com.vachana.qa.models.Customer;
import org.openqa.selenium.By;

public class AccountInformationPage extends BasePage {
    private final By accountInformationHeader = By.xpath("//b[normalize-space()='Enter Account Information']");
    private final By titleMr = By.id("id_gender1");
    private final By password = By.id("password");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletter = By.id("newsletter");
    private final By optIn = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private final By accountCreated = By.cssSelector("[data-qa='account-created']");
    private final By accountDeleted = By.cssSelector("[data-qa='account-deleted']");
    private final By continueButton = By.cssSelector("[data-qa='continue-button']");

    public boolean isLoaded() {
        return elements.isDisplayed(accountInformationHeader);
    }

    public void completeRegistration(Customer customer) {
        elements.click(titleMr);
        elements.type(password, customer.password());
        elements.selectByVisibleText(day, "1");
        elements.selectByVisibleText(month, "January");
        elements.selectByVisibleText(year, "1990");
        elements.click(newsletter);
        elements.click(optIn);
        elements.type(firstName, customer.firstName());
        elements.type(lastName, customer.lastName());
        elements.type(company, customer.company());
        elements.type(address1, customer.address1());
        elements.type(address2, customer.address2());
        elements.selectByVisibleText(country, customer.country());
        elements.type(state, customer.state());
        elements.type(city, customer.city());
        elements.type(zipcode, customer.zipcode());
        elements.type(mobileNumber, customer.mobileNumber());
        elements.click(createAccountButton);
    }

    public boolean accountCreatedVisible() {
        return elements.isDisplayed(accountCreated);
    }

    public boolean accountDeletedVisible() {
        return elements.isDisplayed(accountDeleted);
    }

    public void continueAfterAccountCreated() {
        elements.click(continueButton);
    }
}
