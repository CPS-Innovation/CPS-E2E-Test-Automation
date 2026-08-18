@regression @caseReview @ui
Feature: CR-UI-S21 - RASSO Initial PCD Review
  As a prosecutor lawyer,
  I want to complete RASSO initial 5 day PCD reviews for Threshold Test
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "multi suspect same single offence"
    And precharge the "RASSO" triage case for "5Day" PCD review


  @ui @CR_UI_16554 @CR_UI_S21 @thresholdWith7daysActionPlan
  Scenario: 5 days RASSO PCD review of test type Threshold with 14 days action plan for multi suspect single offence
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    When I make charging decision for the multi defendants as following:
      | decision type             | decision code                                              | reason | out come of case | offence category | charge code decision |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |
      | Further evidence required | H - Request further evidence to complete evidential report |        |                  | Robbery          |                      |
      | Non-conviction disposal   | C - Simple caution                                         |        | D77              | Robbery          |                      |
      | Charge                    | A - Charge and request evidential file                     | D81    | D80              | Robbery          | Accept               |

    And I add an action point plan for "14 days" and "Record of taped interview"
    And I submit the charging decision as following
      | Investigative stage        | Method |
      | Bail for charging decision | Area   |

    Then review is submitted successfully
