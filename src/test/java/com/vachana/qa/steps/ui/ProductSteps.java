package com.vachana.qa.steps.ui;

import com.vachana.qa.pages.CartPage;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.ProductDetailsPage;
import com.vachana.qa.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ProductSteps {
    @When("I open the Products page")
    public void iOpenTheProductsPage() {
        new HomePage().goToProducts();
    }

    @Then("all products should be visible")
    public void allProductsShouldBeVisible() {
        Assert.assertTrue(new ProductsPage().isAllProductsVisible(), "All products page should show product cards");
    }

    @When("I search for product {string}")
    public void iSearchForProduct(String term) {
        new ProductsPage().searchFor(term);
    }

    @Then("search results should contain {string}")
    public void searchResultsShouldContain(String term) {
        ProductsPage productsPage = new ProductsPage();
        Assert.assertTrue(productsPage.searchedProductsVisible(), "Searched Products heading should be visible");
        Assert.assertTrue(productsPage.searchResultsContain(term), "Search results should contain the requested term");
    }

    @When("I open the first product detail page")
    public void iOpenTheFirstProductDetailPage() {
        new ProductsPage().openFirstProduct();
    }

    @Then("the product detail should include core merchandising information")
    public void theProductDetailShouldIncludeCoreMerchandisingInformation() {
        Assert.assertTrue(new ProductDetailsPage().coreDetailsVisible(), "Product details should include name, category, price, availability, condition, and brand");
    }

    @When("I add product {string} to the cart")
    public void iAddProductToTheCart(String productId) {
        new ProductsPage().addProductById(productId);
    }

    @When("I continue shopping")
    public void iContinueShopping() {
        new ProductsPage().continueShopping();
    }

    @When("I view the cart from the add to cart modal")
    public void iViewTheCartFromTheAddToCartModal() {
        new ProductsPage().viewCartFromModal();
    }

    @Then("the cart should contain {int} products")
    public void theCartShouldContainProducts(int expectedCount) {
        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartVisible(), "Cart page should be visible");
        Assert.assertEquals(cartPage.itemCount(), expectedCount, "Unexpected product count in cart");
    }

    @When("I set product quantity to {int} from product details and add it to cart")
    public void iSetProductQuantityToFromProductDetailsAndAddItToCart(int quantity) {
        ProductDetailsPage productDetailsPage = new ProductDetailsPage();
        productDetailsPage.setQuantity(quantity);
        productDetailsPage.addToCart();
        productDetailsPage.viewCartFromModal();
    }

    @Then("the cart quantity should be {int}")
    public void theCartQuantityShouldBe(int expectedQuantity) {
        Assert.assertEquals(new CartPage().firstItemQuantity(), expectedQuantity, "Cart quantity should match product detail input");
    }

    @When("I remove the first product from the cart")
    public void iRemoveTheFirstProductFromTheCart() {
        new CartPage().removeFirstItem();
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertTrue(new CartPage().isCartEmpty(), "Cart should be empty after removing the product");
    }
}
