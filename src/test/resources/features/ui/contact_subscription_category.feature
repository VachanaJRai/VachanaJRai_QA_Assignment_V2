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

  @smoke
  Scenario: Navigate to category products
    Given I am on the Automation Exercise home page
    Then the category sidebar should be visible
    When I open "Women" category "Tops"
    Then the category products heading should contain "Women - Tops Products"
