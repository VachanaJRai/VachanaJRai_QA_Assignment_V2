@api
Feature: Automation Exercise product APIs
  API responses should expose stable schemas, business response codes, and useful validation messages.

  @smoke
  Scenario: Get all products with schema validation
    When I send a GET request to products list API
    Then the HTTP status should be 200
    And the business response code should be 200
    And the products list schema should be valid
    And the response time should be below 5000 milliseconds

  @smoke
  Scenario: Get all brands with schema validation
    When I send a GET request to brands list API
    Then the HTTP status should be 200
    And the business response code should be 200
    And the brands list schema should be valid
    And the response time should be below 5000 milliseconds

  @smoke
  Scenario: Search product API returns matching products
    When I search products through API with term "top"
    Then the HTTP status should be 200
    And the business response code should be 200
    And at least one product should be returned
    And the response time should be below 5000 milliseconds

  @negative
  Scenario: Search product API rejects missing required parameter
    When I call search product API without required parameter
    Then the HTTP status should be 200
    And the business response code should be 400
    And the API message should contain "search_product parameter is missing"
    And the message response schema should be valid

  @negative
  Scenario: Products list API rejects unsupported POST
    When I send unsupported POST request to products list API
    Then the HTTP status should be 200
    And the business response code should be 405
    And the API message should contain "request method is not supported"
    And the message response schema should be valid

  @negative
  Scenario: Brands list API rejects unsupported PUT
    When I send unsupported PUT request to brands list API
    Then the HTTP status should be 200
    And the business response code should be 405
    And the API message should contain "request method is not supported"
    And the message response schema should be valid

  @negative
  Scenario: Verify login API rejects unsupported DELETE
    When I send unsupported DELETE request to verify login API
    Then the HTTP status should be 200
    And the business response code should be 405
    And the API message should contain "request method is not supported"
    And the message response schema should be valid

  @smoke @cleanupUser
  Scenario: Create, update, and delete account through API
    When I create a user account through API
    Then the HTTP status should be 200
    And the business response code should be 201
    And the API message should contain "User created"
    When I update the same user account through API
    Then the HTTP status should be 200
    And the business response code should be 200
    And the API message should contain "User updated"
    When I delete the same user account through API
    Then the HTTP status should be 200
    And the business response code should be 200
    And the API message should contain "Account deleted"
