package com.vachana.qa.pages;

import com.vachana.qa.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

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
    private final By recommendedItems = By.cssSelector(".recommended_items");
    private final By recommendedAddToCartButtons = By.cssSelector("#recommended-item-carousel .item.active a.add-to-cart");
    private final By nextCarouselArrow = By.cssSelector("#slider-carousel a.right");
    private final By previousCarouselArrow = By.cssSelector("#slider-carousel a.left");
    private final By scrollUpArrow = By.id("scrollUp");

    public void open() {
        driver.get(ConfigManager.get("base.url"));
        try {
            waits.visible(By.tagName("body"));
        } catch (RuntimeException ignored) {
            // The public site can keep third-party scripts pending; body visibility is enough for test navigation.
        }
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
        if (!elements.isDisplayed(logoutLink)) {
            open();
        }
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

    public void addFirstRecommendedItemToCart() {
        elements.scrollIntoView(recommendedItems);
        elements.clickFirstDisplayed(recommendedAddToCartButtons);
    }

    public void viewCartFromModal() {
        viewCartFromAddToCartModal();
    }

    public int activeCarouselIndex() {
        Object index = ((JavascriptExecutor) driver).executeScript("""
                return Array.from(document.querySelectorAll('#slider-carousel .item'))
                    .findIndex(element => element.classList.contains('active'));
                """);
        return ((Number) index).intValue();
    }

    public int moveToNextCarouselBanner() {
        int previousIndex = activeCarouselIndex();
        elements.click(nextCarouselArrow);
        return waitForCarouselIndexToChange(previousIndex);
    }

    public int moveToPreviousCarouselBanner() {
        int previousIndex = activeCarouselIndex();
        elements.click(previousCarouselArrow);
        return waitForCarouselIndexToChange(previousIndex);
    }

    public void scrollToFooter() {
        elements.scrollIntoView(subscriptionEmail);
    }

    public boolean subscriptionSectionVisible() {
        return elements.isDisplayed(subscriptionEmail);
    }

    public void clickScrollUpArrow() {
        elements.click(scrollUpArrow);
        waitUntilAtTop();
    }

    public void scrollToTopWithoutArrow() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        waitUntilAtTop();
    }

    public boolean heroTextVisible() {
        return elements.isDisplayed(containsText("Full-Fledged practice website for Automation Engineers"));
    }

    private int waitForCarouselIndexToChange(int previousIndex) {
        return waits.until(webDriver -> {
            int currentIndex = activeCarouselIndex();
            return currentIndex == previousIndex ? null : currentIndex;
        });
    }

    private void waitUntilAtTop() {
        waits.until(webDriver -> {
            Object scrollPosition = ((JavascriptExecutor) webDriver).executeScript("return Math.round(window.scrollY);");
            return ((Number) scrollPosition).longValue() == 0 ? true : null;
        });
    }
}
