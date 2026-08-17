@regression @caseReview @ui
Feature: CR-UI-S15_S3_S9 - RASSO Initial PCD Review
  As a prosecutor lawyer,
  I want to complete RASSO initial 5 day PCD reviews for Threshold Test
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "multi suspect multi offence"
    And precharge the "RASSO" triage case for "5Day" PCD review


  @ui @CR_UI_16554 @CR_UI_S21 @thresholdWith7daysActionPlan
  Scenario: 5 days RASSO PCD review of test type Threshold with 14 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

#    And I preview pre charge analysis
#    When I make charging decision as following:
#      | decision type             | decision code                                              | offence category |
#      | Non-conviction disposal | H - Request further evidence to complete evidential report | Robbery          |
#
#    And I add an action point plan for "14 days" and "Record of taped interview"
#    And I submit the charging decision as following
#      | Investigative stage        | Method | Create MG3 document |
#      | Bail for charging decision | Area   | False               |
#
#    Then review is submitted successfully
