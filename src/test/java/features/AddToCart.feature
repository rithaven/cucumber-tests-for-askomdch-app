Feature: Add product to cart

  Scenario: User adds a featured product to the cart
    Given user is on the home page
    When user adds a featured product to the cart
    And user clicks on View Cart
    Then the cart page should display the selected product
    And the product quantity should be 1
