Feature: Login Swag Labs
  As a customer
  I want to logged in
  So that I can purchase products

  @regression
  Scenario Outline: Validate input Username or Password
    Given I am on login screen
    And I logged in with "<username>" and "<password>"
    Then I should see the "<message>"
    Examples:
      | username        | password     | message                                                      |
      |                 |              | Username is required                                         |
      | standard_user   |              | Password is required                                         |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out.                        |
      | invalid_user    | invalid_pass | Username and password do not match any user in this service. |

  @regression
  Scenario Outline: Logged in on Swag Labs
    Given I am on login screen
    And I logged in with "<username>" and "<password>"
    Then I should see the Homepage
    Examples:
      | username      | password     |  |
      | standard_user | secret_sauce |  |