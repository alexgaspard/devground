Feature: a user can be added

  Scenario: client creates a user
    Given there is no user
    When the client creates a user
    Then the client receives user id
