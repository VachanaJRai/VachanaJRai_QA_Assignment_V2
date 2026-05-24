package com.vachana.qa.steps.ui;

import com.vachana.qa.context.TestContext;
import com.vachana.qa.models.Customer;
import com.vachana.qa.pages.CartPage;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.ProductDetailsPage;
import com.vachana.qa.pages.ProductsPage;
import com.vachana.qa.utils.RandomDataUtils;
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

    @When("I add the first visible product to the cart")
    public void iAddTheFirstVisibleProductToTheCart() {
        new ProductsPage().addFirstVisibleProductToCart();
    }

    @Then("the cart should contain {int} products")
    public void theCartShouldContainProducts(int expectedCount) {
        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartVisible(), "Cart page should be visible");
        Assert.assertEquals(cartPage.itemCount(), expectedCount, "Unexpected product count in cart");
    }

    @Then("the cart should contain at least {int} product(s)")
    public void theCartShouldContainAtLeastProducts(int minimumCount) {
        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartVisible(), "Cart page should be visible");
        Assert.assertTrue(cartPage.itemCount() >= minimumCount, "Cart should contain at least " + minimumCount + " product(s)");
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

    @Then("the brands sidebar should be visible")
    public void theBrandsSidebarShouldBeVisible() {
        Assert.assertTrue(new ProductsPage().brandsSidebarVisible(), "Brands sidebar should be visible");
    }

    @When("I open brand {string}")
    public void iOpenBrand(String brandName) {
        new ProductsPage().openBrand(brandName);
    }

    @Then("the brand products heading should contain {string}")
    public void theBrandProductsHeadingShouldContain(String expectedHeading) {
        Assert.assertTrue(new ProductsPage().productsHeadingContains(expectedHeading),
                "Brand products heading should contain expected brand");
    }

    @Then("filtered products should be visible")
    public void filteredProductsShouldBeVisible() {
        Assert.assertTrue(new ProductsPage().filteredProductsVisible(), "Filtered products should be visible");
    }

    @When("I remember the current cart product count")
    public void iRememberTheCurrentCartProductCount() {
        TestContext.put("cartProductCount", new CartPage().itemCount());
    }

    @Then("the cart should still contain the remembered products")
    public void theCartShouldStillContainTheRememberedProducts() {
        int expectedCount = TestContext.get("cartProductCount", Integer.class);
        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartVisible(), "Cart page should be visible");
        Assert.assertEquals(cartPage.itemCount(), expectedCount, "Cart product count should persist after login");
    }

    @When("I submit a review for the product")
    public void iSubmitAReviewForTheProduct() {
        Customer customer = RandomDataUtils.uniqueCustomer();
        new ProductDetailsPage().submitReview(
                customer.fullName(),
                customer.email(),
                "The product detail page accepted this fake automation review."
        );
    }

    @Then("the review success message should be visible")
    public void theReviewSuccessMessageShouldBeVisible() {
        Assert.assertTrue(new ProductDetailsPage().reviewSuccessVisible(), "Review success message should be visible");
    }

    @When("I add a product from recommended items to the cart")
    public void iAddAProductFromRecommendedItemsToTheCart() {
        new HomePage().addFirstRecommendedItemToCart();
    }
}
