@ui @engagement
Feature: Contact, subscription, and navigation
  Shoppers should be able to contact the site, subscribe, and browse categories.

  @smoke
  Scenario: Submit the contact us form with an upload
    Given I am on the Automation Exercise home page
    When I open the Contact Us page
    And I submit the contact us form with test data
    Then the contact success message should be visible

  @smoke
  Scenario: Subscribe from the home page footer
    Given I am on the Automation Exercise home page
    When I subscribe with a unique email from the footer
    Then the subscription success message should be visible

  @regression
  Scenario: Verify Test Cases page
    Given I am on the Automation Exercise home page
    When I open the Test Cases page
    Then the Test Cases page should be visible

  @regression
  Scenario: Subscribe from the cart page footer
    Given I am on the Automation Exercise home page
    When I open the Cart page
    And I subscribe with a unique email from the footer
    Then the subscription success message should be visible

  @smoke
  Scenario: Navigate to category products
    Given I am on the Automation Exercise home page
    Then the category sidebar should be visible
    When I open "Women" category "Tops"
    Then the category products heading should contain "Women - Tops Products"

  @regression
  Scenario: Filter products by category and brand
    Given I am on the Automation Exercise home page
    Then the category sidebar should be visible
    When I open "Women" category "Tops"
    Then the category products heading should contain "Women - Tops Products"
    And filtered products should be visible
    When I open brand "Polo"
    Then the brand products heading should contain "Brand - Polo Products"
    And filtered products should be visible

  @regression
  Scenario: Home carousel changes with left and right arrows
    Given I am on the Automation Exercise home page
    When I move the home carousel to the next banner
    Then the home carousel banner should change
    When I move the home carousel to the previous banner
    Then the home carousel banner should change again

  @regression
  Scenario: Scroll down and up using arrow button
    Given I am on the Automation Exercise home page
    When I scroll down to the footer
    Then the subscription section should be visible
    When I click the scroll up arrow
    Then the home page hero text should be visible

  @regression
  Scenario: Scroll down and up without arrow button
    Given I am on the Automation Exercise home page
    When I scroll down to the footer
    Then the subscription section should be visible
    When I scroll up to the top without using the arrow
    Then the home page hero text should be visible
