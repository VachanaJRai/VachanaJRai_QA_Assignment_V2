package com.vachana.qa.pages;

import org.openqa.selenium.By;

public class CategoryPage extends BasePage {
    private final By categoryHeading = By.cssSelector(".features_items h2.title");

    public void openCategory(String parentCategory, String childCategory) {
        By parent = By.xpath("//div[@id='accordian']//a[@href='#" + parentCategory + "']");
        By child = By.xpath("//div[@id='" + parentCategory + "']//a[normalize-space()=\"" + childCategory + "\"]");
        elements.click(parent);
        elements.click(child);
    }

    public boolean headingContains(String expectedText) {
        return elements.text(categoryHeading).contains(expectedText.toUpperCase());
    }
}
