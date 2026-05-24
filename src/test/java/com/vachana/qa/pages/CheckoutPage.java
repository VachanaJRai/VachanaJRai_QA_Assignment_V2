package com.vachana.qa.pages;

import com.vachana.qa.models.Customer;
import org.openqa.selenium.By;

import java.util.List;

public class CheckoutPage extends BasePage {
    private final By addressDetails = By.xpath("//h2[normalize-space()='Address Details']");
    private final By reviewOrder = By.xpath("//h2[normalize-space()='Review Your Order']");
    private final By deliveryAddress = By.id("address_delivery");
    private final By invoiceAddress = By.id("address_invoice");
    private final By comment = By.name("message");

    public boolean addressAndOrderReviewVisible() {
        return elements.isDisplayed(addressDetails) && elements.isDisplayed(reviewOrder);
    }

    public boolean addressDetailsMatch(Customer customer) {
        return addressAndOrderReviewVisible()
                && addressBlockMatches(elements.text(deliveryAddress), customer)
                && addressBlockMatches(elements.text(invoiceAddress), customer);
    }

    public void placeOrder(String orderComment) {
        elements.type(comment, orderComment);
        openPath("/payment");
    }

    private boolean addressBlockMatches(String addressBlock, Customer customer) {
        String normalizedAddress = normalize(addressBlock);
        List<String> expectedDetails = List.of(
                customer.fullName(),
                customer.company(),
                customer.address1(),
                customer.address2(),
                customer.city(),
                customer.state(),
                customer.zipcode(),
                customer.country(),
                customer.mobileNumber()
        );

        return expectedDetails.stream()
                .map(this::normalize)
                .allMatch(normalizedAddress::contains);
    }

    private String normalize(String value) {
        return value.replaceAll("\\s+", " ").trim();
    }
}
