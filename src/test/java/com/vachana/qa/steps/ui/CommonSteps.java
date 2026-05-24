package com.vachana.qa.steps.ui;

import com.vachana.qa.pages.CartPage;
import com.vachana.qa.pages.HomePage;
import com.vachana.qa.pages.TestCasesPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CommonSteps {
    @Given("I am on the Automation Exercise home page")
    public void iAmOnTheAutomationExerciseHomePage() {
        Assert.assertTrue(new HomePage().isHomeVisible(), "Home page should be visible");
    }

    @Then("the category sidebar should be visible")
    public void theCategorySidebarShouldBeVisible() {
        Assert.assertTrue(new HomePage().categorySidebarVisible(), "Category sidebar should be visible");
    }

    @When("I open the Cart page")
    public void iOpenTheCartPage() {
        new CartPage().open();
    }

    @When("I open the Test Cases page")
    public void iOpenTheTestCasesPage() {
        new HomePage().goToTestCases();
    }

    @Then("the Test Cases page should be visible")
    public void theTestCasesPageShouldBeVisible() {
        Assert.assertTrue(new TestCasesPage().isLoaded(), "Test Cases page should be visible");
    }
}
