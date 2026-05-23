@ui @registration
Feature: Customer registration
  New customers should be able to create and clean up throwaway accounts without manual setup.

  @smoke
  Scenario: Register a unique customer through the UI
    Given I am on the Automation Exercise home page
    When I register a unique customer through the UI
    Then the account should be created
    When I continue after account creation
    Then I should be logged in after registration
    When I delete the current account
    Then the account should be deleted
