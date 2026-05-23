package com.vachana.qa.pages;

import org.openqa.selenium.By;

import java.util.Map;

public class PaymentPage extends BasePage {
    private final By nameOnCard = By.cssSelector("[data-qa='name-on-card']");
    private final By cardNumber = By.cssSelector("[data-qa='card-number']");
    private final By cvc = By.cssSelector("[data-qa='cvc']");
    private final By expiryMonth = By.cssSelector("[data-qa='expiry-month']");
    private final By expiryYear = By.cssSelector("[data-qa='expiry-year']");
    private final By payButton = By.cssSelector("[data-qa='pay-button']");
    private final By orderPlaced = By.cssSelector("[data-qa='order-placed']");

    public void payWith(Map<String, Object> paymentData) {
        ensurePaymentPageIsOpen();

        elements.type(nameOnCard, paymentData.get("nameOnCard").toString());
        elements.type(cardNumber, paymentData.get("cardNumber").toString());
        elements.type(cvc, paymentData.get("cvc").toString());
        elements.type(expiryMonth, paymentData.get("expiryMonth").toString());
        elements.type(expiryYear, paymentData.get("expiryYear").toString());
        elements.click(payButton);
    }

    public boolean orderPlacedVisible() {
        return elements.isDisplayed(orderPlaced)
                || elements.isDisplayed(containsText("Your order has been placed successfully"));
    }

    private void ensurePaymentPageIsOpen() {
        if (!currentUrl().contains("/payment")) {
            openPath("/payment");
        }
    }
}