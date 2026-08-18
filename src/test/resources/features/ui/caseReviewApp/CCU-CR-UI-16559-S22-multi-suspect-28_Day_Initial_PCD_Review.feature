@regression @caseReview @ui
Feature: CR-UI-S22 - CCU Initial PCD Review
  As a prosecutor lawyer,
  I want to complete CCU initial 28 day PCD reviews for Full Test Code
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "multi suspect multi offence"
    And precharge the "CCU" triage case for "28Day" PCD review


  @CR_UI_16559 @CR_UI_S22 @fullTestCodeWith1DaysActionPlan
  Scenario: 28 days PCD review of case type CCU and test type Full Test Code with 1 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                               |
      | Suspect-victim relationship | Partner                             |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision for the multi defendants as following:
      | decision type             | decision code                                              | reason | out come of case | offence category | charge code decision |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |
      | Further evidence required | H - Request further evidence to complete evidential report |        |                  | Robbery          |                      |
      | Non-conviction disposal   | C - Simple caution                                         |        | D77              | Robbery          |                      |
      | Charge                    | A - Charge and request evidential file                     | D81    | D80              | Robbery          | Accept               |

    And I add an action point plan with:
      | field              | value                      |
      | Date required by   | 14 days                    |
      | Chaser task        | Yes                        |
      | Chaser task date   | 1 day before date required |
      | Related to suspect | All                        |
      | Action             | Key Exhibits               |

    And I submit the charging decision as following
      | Investigative stage        | Method |
      | Bail for charging decision | Area   |

    Then review is submitted successfully
