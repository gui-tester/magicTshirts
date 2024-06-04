@shopping @store
Feature: Shopping Online

  @abandon @scenario1
  Scenario: Scenario 1 - Clear cart and abandon shopping
    Given Store website has loaded
    And The item Bacardi Breezer - Tropical is successfully added to the cart
    When The clear button is clicked
    Then The cart is clear


  @checkout @scenario2
  Scenario: Scenario 2 - Adjust quantities and checkout
    Given Store website has loaded
    And The item Bacardi Breezer - Tropical is added to the cart
    And A second item Wine - Gato Negro Cabernet is added to the cart
    And The Cart link is clicked
    And Cart page has loaded
    When The quantity of the first item is increased
    And The quantity of the first item is increased
    And The total of items match
    And The total payment amount matches
    And The first item has a reduce button in cart
    And The second item has a delete button in cart
    And The reduce button is clicked for the first item
    And The total of items match
    And The total payment amount matches
    And The delete button is clicked for the second item
    And The total of items match
    And The total payment amount matches
    And The checkout button is clicked
    Then The Checkout successfully message is displayed
    And The cart is clear