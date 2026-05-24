package com.vachana.qa.steps.ui;

import com.vachana.qa.context.TestContext;
import com.vachana.qa.models.Customer;
import com.vachana.qa.pages.AccountInformationPage;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.LoginSignupPage;
import com.vachana.qa.utils.RandomDataUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class RegistrationSteps {
    @When("I register a unique customer through the UI")
    public void iRegisterAUniqueCustomerThroughTheUi() {
        new HomePage().goToSignupLogin();
        completeSignupForUniqueCustomer();
    }

    @When("I complete signup for a unique customer")
    public void iCompleteSignupForAUniqueCustomer() {
        completeSignupForUniqueCustomer();
    }

    @Then("the account should be created")
    public void theAccountShouldBeCreated() {
        Assert.assertTrue(new AccountInformationPage().accountCreatedVisible(), "Account created confirmation should be visible");
    }

    @When("I continue after account creation")
    public void iContinueAfterAccountCreation() {
        new AccountInformationPage().continueAfterAccountCreated();
        new HomePage().open();
    }

    @Then("I should be logged in after registration")
    public void iShouldBeLoggedInAfterRegistration() {
        Customer customer = TestContext.get("customer", Customer.class);
        Assert.assertTrue(new HomePage().isLoggedInAs(customer.name()), "Newly registered customer should be logged in");
    }

    @When("I delete the current account")
    public void iDeleteTheCurrentAccount() {
        new HomePage().deleteAccount();
    }

    @Then("the account should be deleted")
    public void theAccountShouldBeDeleted() {
        Assert.assertTrue(new AccountInformationPage().accountDeletedVisible(), "Account deleted confirmation should be visible");
        new HomePage().continueAfterAccountAction();
    }

    private void completeSignupForUniqueCustomer() {
        Customer customer = RandomDataUtils.uniqueCustomer();
        TestContext.put("customer", customer);
        new LoginSignupPage().startSignup(customer.name(), customer.email());

        AccountInformationPage accountInformationPage = new AccountInformationPage();
        Assert.assertTrue(accountInformationPage.isLoaded(), "Account information page should be visible");
        accountInformationPage.completeRegistration(customer);
    }
}
