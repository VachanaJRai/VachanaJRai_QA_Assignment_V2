package com.vachana.qa.steps.ui;

import com.vachana.qa.constants.FrameworkConstants;
import com.vachana.qa.context.TestContext;
import com.vachana.qa.models.Customer;
import com.vachana.qa.pages.CartPage;
import com.vachana.qa.pages.CheckoutPage;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.PaymentPage;
import com.vachana.qa.utils.DownloadUtils;
import com.vachana.qa.utils.TestDataReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import java.nio.file.Path;
import java.util.Map;

public class CheckoutSteps {
    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        new CartPage().proceedToCheckout();
    }

    @When("I proceed to checkout as a guest")
    public void iProceedToCheckoutAsAGuest() {
        new CartPage().proceedToCheckout();
    }

    @Then("the checkout login prompt should be visible")
    public void theCheckoutLoginPromptShouldBeVisible() {
        Assert.assertTrue(new CartPage().checkoutLoginPromptVisible(), "Checkout login prompt should be visible");
    }

    @When("I choose Register Login from the checkout prompt")
    public void iChooseRegisterLoginFromTheCheckoutPrompt() {
        new CartPage().chooseRegisterLoginFromCheckoutPrompt();
    }

    @Then("I should see address details and order review")
    public void iShouldSeeAddressDetailsAndOrderReview() {
        Assert.assertTrue(new CheckoutPage().addressAndOrderReviewVisible(), "Checkout should show address and review sections");
    }

    @Then("the checkout address details should match the registered customer")
    public void theCheckoutAddressDetailsShouldMatchTheRegisteredCustomer() {
        Customer customer = TestContext.get("customer", Customer.class);
        Assert.assertTrue(new CheckoutPage().addressDetailsMatch(customer), "Checkout delivery and billing address should match registered customer");
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

    @When("I download the invoice")
    public void iDownloadTheInvoice() {
        DownloadUtils.clearDownloadDirectory();
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.downloadInvoice();
        Path invoice;
        try {
            invoice = DownloadUtils.waitForDownloadContaining("invoice");
        } catch (TimeoutException exception) {
            paymentPage.downloadInvoice();
            invoice = DownloadUtils.waitForDownloadContaining("invoice");
        }
        TestContext.put("downloadedInvoice", invoice);
    }

    @Then("the invoice should be downloaded successfully")
    public void theInvoiceShouldBeDownloadedSuccessfully() {
        Path invoice = TestContext.get("downloadedInvoice", Path.class);
        Assert.assertTrue(invoice.toFile().exists(), "Invoice file should exist");
        Assert.assertTrue(invoice.toFile().length() > 0, "Invoice file should not be empty");
    }

    @When("I continue after order confirmation")
    public void iContinueAfterOrderConfirmation() {
        new PaymentPage().continueAfterOrder();
        new HomePage().open();
    }

    @Then("the cart should be empty and checkout should be unavailable")
    public void theCartShouldBeEmptyAndCheckoutShouldBeUnavailable() {
        Assert.assertTrue(new CartPage().checkoutUnavailableWhenEmpty(), "Empty cart should not expose checkout action");
    }
}
