package com.vachana.qa.api;

import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class ApiAssertions {
    private ApiAssertions() {
    }

    public static void assertHttpStatus(Response response, int expectedStatus) {
        Assert.assertEquals(response.statusCode(), expectedStatus, "Unexpected HTTP status code");
    }

    public static void assertBusinessCode(Response response, int expectedCode) {
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), expectedCode, "Unexpected API responseCode");
    }

    public static void assertMessageContains(Response response, String expectedMessage) {
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains(expectedMessage),
                "Expected message to contain '" + expectedMessage + "' but was '" + message + "'");
    }

    public static void assertResponseTimeBelow(Response response, long maxMillis) {
        Assert.assertTrue(response.time() < maxMillis,
                "Expected response time below " + maxMillis + " ms but was " + response.time() + " ms");
    }

    public static void assertSchema(Response response, String schemaPath) {
        response.then().body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
