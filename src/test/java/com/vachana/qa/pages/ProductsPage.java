package com.vachana.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Locale;

public class ProductsPage extends BasePage {
    private final By allProductsTitle = By.xpath("//h2[normalize-space()='All Products']");
    private final By searchedProductsTitle = By.xpath("//h2[normalize-space()='Searched Products']");
    private final By productCards = By.cssSelector(".features_items .product-image-wrapper");
    private final By productNames = By.cssSelector(".features_items .productinfo p");
    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By visibleProductAddToCartButtons = By.cssSelector(".features_items .productinfo a.add-to-cart");
    private final By brandsSidebar = By.cssSelector(".brands-name");
    private final By productsHeading = By.cssSelector(".features_items h2.title");

    public boolean isAllProductsVisible() {
        return elements.isDisplayed(allProductsTitle) && elements.elements(productCards).size() > 0;
    }

    public void searchFor(String term) {
        elements.type(searchInput, term);
        elements.click(searchButton);
    }

    public boolean searchedProductsVisible() {
        return elements.isDisplayed(searchedProductsTitle);
    }

    public boolean searchResultsContain(String term) {
        String normalizedTerm = term.toLowerCase(Locale.ROOT);
        List<String> names = elements.elements(productNames)
                .stream()
                .filter(WebElement::isDisplayed)
                .map(element -> element.getText().toLowerCase(Locale.ROOT))
                .toList();

        return !names.isEmpty() && names.stream().anyMatch(name -> name.contains(normalizedTerm));
    }

    public int productCount() {
        return elements.elements(productCards).size();
    }

    public void openFirstProduct() {
        openPath("/product_details/1");
    }

    public void addProductById(String productId) {
        By product = By.xpath("(//div[contains(@class,'productinfo')]//a[@data-product-id='" + productId + "'])[1]");
        elements.click(product);
    }

    public void addFirstVisibleProductToCart() {
        elements.clickFirstDisplayed(visibleProductAddToCartButtons);
    }

    public void continueShopping() {
        continueShoppingFromAddToCartModal();
    }

    public void viewCartFromModal() {
        viewCartFromAddToCartModal();
    }

    public boolean brandsSidebarVisible() {
        return elements.isDisplayed(brandsSidebar);
    }

    public void openBrand(String brandName) {
        By brandLink = By.xpath("//div[contains(@class,'brands-name')]//a[contains(normalize-space(),\"" + brandName + "\")]");
        elements.click(brandLink);
    }

    public boolean productsHeadingContains(String expectedText) {
        return elements.text(productsHeading).toLowerCase(Locale.ROOT)
                .contains(expectedText.toLowerCase(Locale.ROOT));
    }

    public boolean filteredProductsVisible() {
        return productCount() > 0;
    }
}
