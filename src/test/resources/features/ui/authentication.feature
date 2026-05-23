@ui @auth
Feature: Authentication
  Registered users should be able to login, logout, and see clear validation for invalid access.

  @smoke @requiresRegisteredUser @cleanupUser
  Scenario: Login with a valid registered customer
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    Then the Signup Login page should show login and signup panels
    When I login with the registered customer
    Then I should be logged in as that customer

  @negative
  Scenario Outline: Reject invalid login credentials
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    Then the Signup Login page should show login and signup panels
    When I login with email "<email>" and password "<password>"
    Then I should see invalid login message

    Examples:
      | email                        | password          |
      | missing-user@example.invalid | WrongPassword@123 |
      | qa@example.invalid           |                   |

  @smoke @requiresRegisteredUser @cleanupUser
  Scenario: Logout returns the customer to login page
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    And I login with the registered customer
    Then I should be logged in as that customer
    When I logout
    Then I should be returned to the login page

  @negative @requiresRegisteredUser @cleanupUser
  Scenario: Signup rejects an already registered email address
    Given I am on the Automation Exercise home page
    When I navigate to the Signup Login page
    And I attempt signup with the registered customer's email
    Then I should see existing email signup validation
