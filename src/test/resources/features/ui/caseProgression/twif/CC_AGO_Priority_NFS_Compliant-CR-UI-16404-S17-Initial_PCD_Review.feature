@regression @caseReview @ui
Feature: CR-UI-S17 - MC Initial PCD Review for Priority NFS Compliant
  As a prosecutor lawyer,
  I want to complete CC initial NFS Compliant day PCD reviews for Full Code, Threshold
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "ago priority single defendant multi offence"
    And precharge the RED "MC" triage case for "NFS Compliant" PCD review


  @CR_UI_16404 @CR_UI_S17 @fullCodeWithoutActionPlan
  Scenario: Priority NFS Compliant PCD review of test type full code without action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "Priority PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type | decision code                          | charge code decision | AG consent              | reason | out come of case | offence category |
      | Charge        | A - Charge and request evidential file | Accept               | Yes - AG consent given  | D81    | D80              | Robbery          |

    And I continue without action plan
    And I submit the charging decision as following
      | Investigative stage        | Method |
      | Bail for charging decision | Area   |

    Then review is submitted successfully

#  @CR_UI_16281 @CR_UI_S11 @thresholdWith7daysActionPlan
#  Scenario: Priority PCD review of test type Threshold with 7 day action plan
#    Given I login to case review app
#    And Search the case "URN"
#    When I start "Priority PCD Review"
#    And select test as "Threshold Test"
#    And I complete the Threshold Test pre-charge analysis details with:
#      | field                       | value                              |
#      | Suspect-victim relationship | Partner                            |
#      | Global monitoring codes     | Asset Recovery; DA specialist court |
#
#    And I preview pre charge analysis
#    When I make charging decision as following:
#      | decision type | decision code                          | charge code decision | AG consent              | reason | out come of case | offence category |
#      | Charge        | A - Charge and request evidential file | Accept               | Yes - AG consent given  | D81    | D80              | Robbery          |
#
#    And I add an action point plan with:
#      | field              | value                |
#      | Date required by   | 7 days              |
#      | Chaser task        | No                   |
#      | Related to suspect | All                  |
#      | Action             | Suspect |
#
#    And I submit the charging decision as following
#      | Investigative stage        | Method | Create MG3 document |
#      | Bail for charging decision | Area   | true                |
#
#    Then review is submitted successfully
#
