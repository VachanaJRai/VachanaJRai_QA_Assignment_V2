package com.vachana.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Locale;

public class ProductDetailsPage extends BasePage {
    private final By quantity = By.id("quantity");
    private final By addToCart = By.cssSelector("button.cart");
    private final By reviewName = By.id("name");
    private final By reviewEmail = By.id("email");
    private final By reviewText = By.id("review");
    private final By submitReview = By.id("button-review");
    private final By reviewSuccess = By.cssSelector("#review-section .alert-success");

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
        viewCartFromAddToCartModal();
    }

    public void submitReview(String name, String email, String review) {
        elements.type(reviewName, name);
        elements.type(reviewEmail, email);
        elements.type(reviewText, review);
        elements.click(submitReview);
    }

    public boolean reviewSuccessVisible() {
        return elements.isDisplayed(reviewSuccess)
                || elements.isDisplayed(containsText("Thank you for your review."));
    }
}
