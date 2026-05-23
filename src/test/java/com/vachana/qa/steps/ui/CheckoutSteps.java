package com.vachana.qa.steps.ui;

import com.vachana.qa.constants.FrameworkConstants;
import com.vachana.qa.pages.CartPage;
import com.vachana.qa.pages.CheckoutPage;
import com.vachana.qa.pages.PaymentPage;
import com.vachana.qa.utils.TestDataReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

public class CheckoutSteps {
    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        new CartPage().proceedToCheckout();
    }

    @Then("I should see address details and order review")
    public void iShouldSeeAddressDetailsAndOrderReview() {
        Assert.assertTrue(new CheckoutPage().addressAndOrderReviewVisible(), "Checkout should show address and review sections");
    }

    @When("I place the order with comment {string}")
    public void iPlaceTheOrderWithComment(String comment) {
        new CheckoutPage().placeOrder(comment);
    }

    @When("I pay with fake card details")
    public void iPayWithFakeCardDetails() {
        Map<String, Object> paymentData = TestDataReader.section(FrameworkConstants.USER_TEST_DATA, "payment");
        new PaymentPage().payWith(paymentData);
    }

    @Then("the order should be confirmed")
    public void theOrderShouldBeConfirmed() {
        Assert.assertTrue(new PaymentPage().orderPlacedVisible(), "Order confirmation should be visible");
    }
}
