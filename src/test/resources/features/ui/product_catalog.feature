@ui @catalog
Feature: Product catalog and cart
  Shoppers should be able to discover products, validate details, and manage cart contents.

  @smoke
  Scenario Outline: Search products by meaningful keyword
    Given I am on the Automation Exercise home page
    When I open the Products page
    Then all products should be visible
    When I search for product "<term>"
    Then search results should contain "<term>"

    Examples:
      | term  |
      | top   |
      | jean  |

  @smoke
  Scenario: Product detail page shows merchandising information
    Given I am on the Automation Exercise home page
    When I open the Products page
    Then all products should be visible
    And I open the first product detail page
    Then the product detail should include core merchandising information

  @smoke
  Scenario: Add two products to cart and validate line count
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I add product "1" to the cart
    And I continue shopping
    And I add product "2" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 2 products

  @edge
  Scenario: Product detail quantity is carried to the cart
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I open the first product detail page
    And I set product quantity to 4 from product details and add it to cart
    Then the cart quantity should be 4

  @smoke
  Scenario: Remove product from cart
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    When I remove the first product from the cart
    Then the cart should be empty

  @regression
  Scenario: View brand products
    Given I am on the Automation Exercise home page
    When I open the Products page
    Then the brands sidebar should be visible
    When I open brand "Polo"
    Then the brand products heading should contain "Brand - Polo Products"
    And filtered products should be visible
    When I open brand "H&M"
    Then the brand products heading should contain "Brand - H&M Products"
    And filtered products should be visible

  @regression @requiresRegisteredUser @cleanupUser
  Scenario: Search products and verify cart after login
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I search for product "top"
    Then search results should contain "top"
    When I add the first visible product to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain at least 1 product
    When I remember the current cart product count
    And I navigate to the Signup Login page
    And I login with the registered customer
    Then I should be logged in as that customer
    When I open the Cart page
    Then the cart should still contain the remembered products

  @regression
  Scenario: Add review on product
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I open the first product detail page
    And I submit a review for the product
    Then the review success message should be visible

  @regression
  Scenario: Add to cart from recommended items
    Given I am on the Automation Exercise home page
    When I add a product from recommended items to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain at least 1 product

  @edge
  Scenario: Adding the same product twice increases quantity
    Given I am on the Automation Exercise home page
    When I open the Products page
    And I add product "1" to the cart
    And I continue shopping
    And I add product "1" to the cart
    And I view the cart from the add to cart modal
    Then the cart should contain 1 products
    And the cart quantity should be 2
