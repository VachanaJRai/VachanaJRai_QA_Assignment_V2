package com.vachana.qa.steps.api;

import com.vachana.qa.api.ApiAssertions;
import com.vachana.qa.api.ApiClient;
import com.vachana.qa.api.ApiEndpoints;
import com.vachana.qa.api.ApiPayloadFactory;
import com.vachana.qa.constants.FrameworkConstants;
import com.vachana.qa.context.TestContext;
import com.vachana.qa.models.Customer;
import com.vachana.qa.utils.RandomDataUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class ApiSteps {
    private final ApiClient apiClient = new ApiClient();
    private Response response;

    @When("I send a GET request to products list API")
    public void iSendAGetRequestToProductsListApi() {
        response = apiClient.get(ApiEndpoints.PRODUCTS_LIST);
    }

    @When("I send a GET request to brands list API")
    public void iSendAGetRequestToBrandsListApi() {
        response = apiClient.get(ApiEndpoints.BRANDS_LIST);
    }

    @When("I search products through API with term {string}")
    public void iSearchProductsThroughApiWithTerm(String term) {
        response = apiClient.postForm(ApiEndpoints.SEARCH_PRODUCT, Map.of("search_product", term));
    }

    @When("I call search product API without required parameter")
    public void iCallSearchProductApiWithoutRequiredParameter() {
        response = apiClient.post(ApiEndpoints.SEARCH_PRODUCT);
    }

    @When("I send unsupported POST request to products list API")
    public void iSendUnsupportedPostRequestToProductsListApi() {
        response = apiClient.post(ApiEndpoints.PRODUCTS_LIST);
    }

    @When("I send unsupported PUT request to brands list API")
    public void iSendUnsupportedPutRequestToBrandsListApi() {
        response = apiClient.put(ApiEndpoints.BRANDS_LIST);
    }

    @When("I send unsupported DELETE request to verify login API")
    public void iSendUnsupportedDeleteRequestToVerifyLoginApi() {
        response = apiClient.delete(ApiEndpoints.VERIFY_LOGIN);
    }

    @When("I create a user account through API")
    public void iCreateAUserAccountThroughApi() {
        Customer customer = RandomDataUtils.uniqueCustomer();
        TestContext.put("customer", customer);
        response = apiClient.postForm(ApiEndpoints.CREATE_ACCOUNT, ApiPayloadFactory.createAccountPayload(customer));
    }

    @When("I update the same user account through API")
    public void iUpdateTheSameUserAccountThroughApi() {
        Customer customer = TestContext.get("customer", Customer.class);
        response = apiClient.putForm(ApiEndpoints.UPDATE_ACCOUNT, ApiPayloadFactory.createAccountPayload(customer));
    }

    @When("I delete the same user account through API")
    public void iDeleteTheSameUserAccountThroughApi() {
        Customer customer = TestContext.get("customer", Customer.class);
        response = apiClient.deleteForm(ApiEndpoints.DELETE_ACCOUNT, ApiPayloadFactory.deletePayload(customer));
        TestContext.remove("customer");
    }

    @Then("the HTTP status should be {int}")
    public void theHttpStatusShouldBe(int expectedStatus) {
        ApiAssertions.assertHttpStatus(response, expectedStatus);
    }

    @Then("the business response code should be {int}")
    public void theBusinessResponseCodeShouldBe(int expectedCode) {
        ApiAssertions.assertBusinessCode(response, expectedCode);
    }

    @Then("the API message should contain {string}")
    public void theApiMessageShouldContain(String expectedMessage) {
        ApiAssertions.assertMessageContains(response, expectedMessage);
    }

    @Then("the response time should be below {int} milliseconds")
    public void theResponseTimeShouldBeBelowMilliseconds(int maxMillis) {
        ApiAssertions.assertResponseTimeBelow(response, maxMillis);
    }

    @Then("the products list schema should be valid")
    public void theProductsListSchemaShouldBeValid() {
        ApiAssertions.assertSchema(response, FrameworkConstants.PRODUCTS_SCHEMA);
    }

    @Then("the brands list schema should be valid")
    public void theBrandsListSchemaShouldBeValid() {
        ApiAssertions.assertSchema(response, FrameworkConstants.BRANDS_SCHEMA);
    }

    @Then("the message response schema should be valid")
    public void theMessageResponseSchemaShouldBeValid() {
        ApiAssertions.assertSchema(response, FrameworkConstants.MESSAGE_SCHEMA);
    }

    @Then("at least one product should be returned")
    public void atLeastOneProductShouldBeReturned() {
        int productCount = response.jsonPath().getList("products").size();
        Assert.assertTrue(productCount > 0, "Search should return at least one product");
    }
}
