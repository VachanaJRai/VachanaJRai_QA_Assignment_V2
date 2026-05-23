package com.vachana.qa.pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    private final By addressDetails = By.xpath("//h2[normalize-space()='Address Details']");
    private final By reviewOrder = By.xpath("//h2[normalize-space()='Review Your Order']");
    private final By comment = By.name("message");

    public boolean addressAndOrderReviewVisible() {
        return elements.isDisplayed(addressDetails) && elements.isDisplayed(reviewOrder);
    }

    public void placeOrder(String orderComment) {
        elements.type(comment, orderComment);
        openPath("/payment");
    }
}