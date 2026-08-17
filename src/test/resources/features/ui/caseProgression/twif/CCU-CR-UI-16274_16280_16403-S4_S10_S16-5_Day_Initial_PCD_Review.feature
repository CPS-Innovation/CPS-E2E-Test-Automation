@regression @caseReview @ui
Feature: CR-UI-S4_S10_S16 - CCU Initial PCD Review
  As a prosecutor lawyer,
  I want to complete CCU initial 5 day PCD reviews for Full Code, Threshold Test and Early Advice
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "single suspect single offence"
    And precharge the "CCU" triage case for "5Day" PCD review


  @ui @CR_UI_16403 @CR_UI_S16 @fullCodeWith1DayActionPlan
  Scenario: 28 days PCD review of test type full code with 28 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type     | decision code                   | reason | out come of case | offence category |
      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |

    And I add an action point plan for "1 days" and "Other (specify)"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully


#  @ui @CR_UI_16280 @CR_UI_S10 @thresholdWithActionPlan
#  Scenario: 5 days PCD review of test type Threshold with 1 day action plan
#    Given I login to case review app
#    And Search the case "URN"
#    When I start "28 day PCD Review"
#    And select test as "Threshold Test"
#    And I complete the Threshold Test pre-charge analysis details with:
#      | field                       | value                              |
#      | Suspect-victim relationship | Partner                            |
#      | Global monitoring codes     | Asset Recovery; DA specialist court |
#
#    And I preview pre charge analysis
#    When I make charging decision as following:
#      | decision type     | decision code                   | reason | out come of case | offence category |
#      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |
#
#    And I add an action point plan for "28 days" and "Key Witness Details"
#    And I submit the charging decision as following
#      | Investigative stage        | Method | Create MG3 document |
#      | Bail for charging decision | Area   | False               |
#
#    Then review is submitted successfully


  @ui @CR_UI_16274 @CR_UI_S4 @earlyAdviceWith14DayActionPlan
   Scenario: 5 days PCD review of case type CCU and test type Early Advice with 14 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Early Advice"
    And I complete the Early Advice pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I select PCD principal offence category as "Robbery"
    And I add an action point plan with:
      | field              | value                      |
      | Date required by   | 14 days                    |
      | Chaser task        | Yes                        |
      | Chaser task date   | 1 day before date required |
      | Related to suspect | All                        |
      | Action             | Key Exhibits               |

    And I submit the charging decision as following
      | Investigative stage        | Create MG3 document |
      | Bail for charging decision | False               |

    Then review is submitted successfully
