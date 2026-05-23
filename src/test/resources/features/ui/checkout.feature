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
