@DCF
Feature: Report validation feature

  @Test1
  Scenario: Passing scenario for report
    Given I print a message "This is a passing scenario"
    Then the test should pass

  @Test2
  Scenario: Failing scenario for report
    Given I print a message "This is a failing scenario"
    Then the test should fail