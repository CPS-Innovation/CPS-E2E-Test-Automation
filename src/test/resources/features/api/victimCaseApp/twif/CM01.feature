Feature: Create a TWIF case

  @atwip
  Scenario Outline: Create a TWIF case - One defendant with a proposed charge

    Given the "TWIF" "CM01" API is available
    When I send a "POST" request with payload "<payload>"
    Then the response status should be <statusCode>
#    And the response should contain "username" in identifier "TBC"

    Examples:
      | payload       | statusCode |
      | twif_cm01.xml | 200        |

