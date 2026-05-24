package com.vachana.qa.pages;

import org.openqa.selenium.By;

public class CartPage extends BasePage {
    private final By cartTable = By.id("cart_info_table");
    private final By cartRows = By.cssSelector("#cart_info_table tbody tr[id^='product-']");
    private final By firstQuantity = By.cssSelector("#cart_info_table tbody tr[id^='product-']:first-child .cart_quantity button");
    private final By firstDelete = By.cssSelector("#cart_info_table tbody tr[id^='product-']:first-child .cart_delete a");
    private final By checkoutButton = By.cssSelector("a.check_out");
    private final By emptyCartMessage = By.xpath("//*[contains(normalize-space(),'Cart is empty')]");
    private final By registerLoginFromCheckoutPrompt = By.cssSelector("#checkoutModal a[href='/login']");

    public void open() {
        openPath("/view_cart");
    }

    public boolean isCartVisible() {
        return currentUrl().contains("/view_cart") && (elements.isDisplayed(cartTable) || elements.isDisplayed(emptyCartMessage));
    }

    public int itemCount() {
        return elements.elements(cartRows).size();
    }

    public int firstItemQuantity() {
        return Integer.parseInt(elements.text(firstQuantity));
    }

    public void removeFirstItem() {
        elements.click(firstDelete);
    }

    public boolean isCartEmpty() {
        return elements.isDisplayed(emptyCartMessage) || itemCount() == 0;
    }

    public void proceedToCheckout() {
        elements.click(checkoutButton);
    }

    public boolean checkoutLoginPromptVisible() {
        return elements.isDisplayed(registerLoginFromCheckoutPrompt)
                || elements.isDisplayed(containsText("Register / Login account to proceed on checkout."));
    }

    public void chooseRegisterLoginFromCheckoutPrompt() {
        elements.click(registerLoginFromCheckoutPrompt);
    }

    public boolean checkoutUnavailableWhenEmpty() {
        return isCartEmpty() && !elements.isDisplayed(checkoutButton);
    }
}
