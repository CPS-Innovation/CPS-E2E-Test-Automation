#dont delete yet i need to check it is not a duplicate

#@regression @CR_UI_S22
#Feature: CR-UI-S22 - CCU Initial PCD Review for multi suspect
#  As a prosecutor lawyer,
#  I want to complete CCU initial 28 day PCD reviews for Full Code
#  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.
#
#  Background: Case Creation
#    Given create new case using "CM01" for type "multi defendant multiple offence"
#    And precharge the "CCU" triage case for "28Day" PCD review
#
#
#  @ui @CR_UI_16559 @CR_UI_S12 @fullCodeWith1DayActionPlan
#  Scenario: 28 days PCD review of test type full code with 28 days action plan
#    Given I login to case review app
#    And Search the case "URN"
#    When I start "5 day PCD Review"
#    And select test as "Full Code Test"
#    And I complete the pre-charge analysis details with:
#      | field                       | value                              |
#      | Suspect-victim relationship | Partner                            |
#      | Global monitoring codes     | Asset Recovery; DA specialist court |
#
#    And I preview pre charge analysis
#    And I choose DG compliant as Yes
#    When I make charging decision as following:
#      | decision type     | decision code                   | reason | out come of case | offence category |
#      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |
#
#    And I add an action point plan for "1 days" and "Other (specify)"
#    And I submit the charging decision as following
#      | Investigative stage        | Method | Create MG3 document |
#      | Bail for charging decision | Area   | true                |
#
#    Then review is submitted successfully
#
