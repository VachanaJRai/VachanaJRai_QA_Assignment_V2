package com.vachana.qa.api;

import com.vachana.qa.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {
    public ApiClient() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public Response get(String endpoint) {
        return given()
                .spec(requestSpecification())
                .when()
                .get(endpoint);
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        return given()
                .spec(requestSpecification())
                .queryParams(queryParams)
                .when()
                .get(endpoint);
    }

    public Response postForm(String endpoint, Map<String, ?> formParams) {
        return given()
                .spec(requestSpecification())
                .formParams(formParams)
                .when()
                .post(endpoint);
    }

    public Response putForm(String endpoint, Map<String, ?> formParams) {
        return given()
                .spec(requestSpecification())
                .formParams(formParams)
                .when()
                .put(endpoint);
    }

    public Response deleteForm(String endpoint, Map<String, ?> formParams) {
        return given()
                .spec(requestSpecification())
                .formParams(formParams)
                .when()
                .delete(endpoint);
    }

    public Response post(String endpoint) {
        return given()
                .spec(requestSpecification())
                .when()
                .post(endpoint);
    }

    public Response put(String endpoint) {
        return given()
                .spec(requestSpecification())
                .when()
                .put(endpoint);
    }

    public Response delete(String endpoint) {
        return given()
                .spec(requestSpecification())
                .when()
                .delete(endpoint);
    }

    private RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("api.base.url"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.URLENC)
                .setRelaxedHTTPSValidation()
                .build();
    }
}
