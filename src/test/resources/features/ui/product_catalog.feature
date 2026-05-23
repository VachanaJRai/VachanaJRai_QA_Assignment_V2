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
