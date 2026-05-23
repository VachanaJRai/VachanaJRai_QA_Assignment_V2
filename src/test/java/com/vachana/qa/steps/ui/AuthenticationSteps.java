package com.vachana.qa.steps.ui;

import com.vachana.qa.context.TestContext;
import com.vachana.qa.models.Customer;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.LoginSignupPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AuthenticationSteps {
    @When("I navigate to the Signup Login page")
    public void iNavigateToTheSignupLoginPage() {
        new HomePage().goToSignupLogin();
    }

    @Then("the Signup Login page should show login and signup panels")
    public void theSignupLoginPageShouldShowLoginAndSignupPanels() {
        Assert.assertTrue(new LoginSignupPage().isLoaded(), "Login and signup panels should be visible");
    }

    @When("I login with the registered customer")
    public void iLoginWithTheRegisteredCustomer() {
        Customer customer = TestContext.get("customer", Customer.class);
        new LoginSignupPage().login(customer.email(), customer.password());
    }

    @When("I login with email {string} and password {string}")
    public void iLoginWithEmailAndPassword(String email, String password) {
        new LoginSignupPage().login(email, password);
    }

    @Then("I should be logged in as that customer")
    public void iShouldBeLoggedInAsThatCustomer() {
        Customer customer = TestContext.get("customer", Customer.class);
        Assert.assertTrue(new HomePage().isLoggedInAs(customer.name()), "Logged in customer name should be displayed");
    }

    @When("I logout")
    public void iLogout() {
        new HomePage().logout();
    }

    @Then("I should be returned to the login page")
    public void iShouldBeReturnedToTheLoginPage() {
        Assert.assertTrue(new LoginSignupPage().isLoaded(), "Login page should be visible after logout");
    }

    @Then("I should see invalid login message")
    public void iShouldSeeInvalidLoginMessage() {
        Assert.assertTrue(new LoginSignupPage().invalidLoginMessageVisible(), "Invalid login message should be visible");
    }

    @When("I attempt signup with the registered customer's email")
    public void iAttemptSignupWithTheRegisteredCustomerSEmail() {
        Customer customer = TestContext.get("customer", Customer.class);
        new LoginSignupPage().startSignup(customer.name(), customer.email());
    }

    @Then("I should see existing email signup validation")
    public void iShouldSeeExistingEmailSignupValidation() {
        Assert.assertTrue(new LoginSignupPage().existingEmailMessageVisible(), "Existing email validation should be visible");
    }
}
