package com.vachana.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Locale;

public class ProductDetailsPage extends BasePage {
    private final By quantity = By.id("quantity");
    private final By addToCart = By.cssSelector("button.cart");
    private final By viewCartInModal = By.xpath("//u[normalize-space()='View Cart']/parent::a");

    public boolean coreDetailsVisible() {
        if (!currentUrl().contains("/product_details/")) {
            return false;
        }

        Boolean hasProductPanel = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('.product-information') !== null;");

        String bodyText = ((JavascriptExecutor) driver)
                .executeScript("return document.body.innerText;")
                .toString()
                .toLowerCase(Locale.ROOT);

        return hasProductPanel
                && bodyText.contains("category:")
                && bodyText.contains("rs.")
                && bodyText.contains("availability:")
                && bodyText.contains("condition:")
                && bodyText.contains("brand:")
                && bodyText.contains("add to cart");
    }

    public void setQuantity(int value) {
        elements.type(quantity, String.valueOf(value));
    }

    public void addToCart() {
        elements.click(addToCart);
    }

    public void viewCartFromModal() {
        elements.click(viewCartInModal);
    }
}