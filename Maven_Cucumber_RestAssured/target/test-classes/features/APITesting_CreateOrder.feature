Feature: Validate Create Order feature
  I want to create a PayPal order using this feature

  Scenario Outline: Validate Create Order feature with valid details
    Given I want to get access token from PayPal api
    When I set currency code as "<currencyCode>" & value as "<currencyValue>" and hit the api
    Then I verify the status as CREATED

    Examples: 
      | currencyCode | currencyValue |
      | USD          |        500.00 |
      | GBP          |        500.00 |
