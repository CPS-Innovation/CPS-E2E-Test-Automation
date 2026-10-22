@regression @dcf @ui
Feature: CR-UI-S2_S14 - CC Initial PCD Review
  As a prosecutor lawyer,
  I want to complete CC initial 5 day PCD reviews for Full Code and Early Advice
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "single suspect single offence"
    And add "victim witness key" using "LM04" for the case
    And add "victim witness vulnerable chain" using "LM04" for the case
    And add "victim witness intimidated chain" using "LM04" for the case
    And add "witness statement" using "LM05" for the case
    And add "unused material" using "LM06" for the case
    And add "case document" using "LM07" for the case
    And add "provide charging decision" using "CJ05" for the case



  @ui @CR_UI_16401 @CR_UI_S14 @fullCodeWith14DayActionPlan
  Scenario: 5 days PCD review of CC test type full code with 14 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "5 day PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type           | decision code      | out come of case | offence category |
      | Non-conviction disposal | C - Simple caution | D77              | Robbery          |

    And I add an action point plan for "14 days" and "Key Exhibits"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully
