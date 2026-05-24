@ui @checkout
Feature: Checkout
  Logged-in customers should be able to place an order with fake payment data.

  @smoke @requiresRegisteredUser @cleanupUser
  Scenario: Place an order as a logged-in customer
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    And I login with the registered customer
    Then I should be logged in as that customer
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I proceed to checkout
    Then I should see address details and order review
    When I place the order with comment "Please deliver this fake test order safely."
    And I pay with fake card details
    Then the order should be confirmed

  @regression @cleanupUser
  Scenario: Place an order after registering during checkout
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I proceed to checkout as a guest
    Then the checkout login prompt should be visible
    When I choose Register Login from the checkout prompt
    And I complete signup for a unique customer
    Then the account should be created
    When I continue after account creation
    Then I should be logged in after registration
    When I open the Cart page
    And I proceed to checkout
    Then I should see address details and order review
    When I place the order with comment "Checkout registration order."
    And I pay with fake card details
    Then the order should be confirmed

  @regression @cleanupUser
  Scenario: Place an order after registering before checkout
    Given I am on the Automation Exercise home page
    When I register a unique customer through the UI
    Then the account should be created
    When I continue after account creation
    Then I should be logged in after registration
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I proceed to checkout
    Then I should see address details and order review
    When I place the order with comment "Pre-checkout registration order."
    And I pay with fake card details
    Then the order should be confirmed

  @regression @requiresRegisteredUser @cleanupUser
  Scenario: Verify address details in checkout page
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    And I login with the registered customer
    Then I should be logged in as that customer
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I proceed to checkout
    Then the checkout address details should match the registered customer

  @regression @requiresRegisteredUser @cleanupUser
  Scenario: Download invoice after purchase order
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    And I login with the registered customer
    Then I should be logged in as that customer
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I proceed to checkout
    Then I should see address details and order review
    When I place the order with comment "Invoice download order."
    And I pay with fake card details
    Then the order should be confirmed
    When I download the invoice
    Then the invoice should be downloaded successfully

  @e2e @cleanupUser
  Scenario: Complete end-to-end purchase flow
    Given I am on the Automation Exercise home page
    When I register a unique customer through the UI
    Then the account should be created
    When I continue after account creation
    Then I should be logged in after registration
    When I open the Products page
    And I search for product "top"
    Then search results should contain "top"
    When I add the first visible product to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain at least 1 product
    When I proceed to checkout
    Then the checkout address details should match the registered customer
    When I place the order with comment "Complete E2E fake purchase."
    And I pay with fake card details
    Then the order should be confirmed
    When I download the invoice
    Then the invoice should be downloaded successfully
    When I continue after order confirmation
    And I logout
    Then I should be returned to the login page

  @edge
  Scenario: Checkout is blocked when cart is empty
    Given I am on the Automation Exercise home page
    When I open the Cart page
    Then the cart should be empty and checkout should be unavailable
