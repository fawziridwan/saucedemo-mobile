Feature: Product Purchase
  As a customer
  I want to purchase products
  So that I can complete my shopping

  @test
  Scenario: Purchase Sauce Labs Backpack and Bike Light
    Given I am on the login screen
    And I login with username "standard_user" and password "secret_sauce"
    Then I should see the Homepage
#    When I add "Sauce Labs Backpack" to cart
#    And I add "Sauce Labs Bike Light" to cart
#    And I go to cart
#    And I proceed to checkout
#    And I enter checkout information:
#      | firstName | LastName | ZipCode |
#      | John      | Doe      | 12345   |
#    And I complete the checkout
#    Then I should see the order completion message