@regression @caseReview @ui
Feature: CR-UI-S8 - CC Initial PCD Review
  As a prosecutor lawyer,
  I want to complete CC initial 5 day PCD reviews for Threshold Test
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "single suspect single offence"
    And precharge the "CC" triage case for "5Day" PCD review


  @ui @CR_UI_16278 @CR_UI_S8 @thresholdWith14DayActionPlan
  Scenario: 5 days PCD review for CC Threshold test type with 14 day action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    When I make charging decision as following:
      | decision type           | decision code      | out come of case | offence category |
      | Non-conviction disposal | C - Simple caution | D77              | Robbery          |

    And I add an action point plan for "7 days" and "Key Witness Details"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully

