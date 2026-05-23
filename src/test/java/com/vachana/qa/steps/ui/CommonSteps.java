package com.vachana.qa.steps.ui;

import com.vachana.qa.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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
}
