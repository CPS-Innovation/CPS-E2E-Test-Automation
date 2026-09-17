@regression @caseReview @ui
Feature: CR-UI-S24 - CC Initial PCD Review for Priority NFS Complaint
  As a prosecutor lawyer,
  I want to complete CC initial 28 day PCD reviews for Full Code test
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "priority multi suspect single offence"
    And precharge the RED "CC" triage case for "NFS Non Compliant" PCD review


  @CR_UI_16556 @CR_UI_S24 @fullTestCodeWith7DaysActionPlan
  Scenario: Priority Multi Suspect 28 days NFS Non Compliant PCD review of case type CC and test type Full Code with7Days action plan
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
    When I make charging decision for the multi defendants as following:
      | decision type             | decision code                                              | reason | out come of case | offence category | charge code decision |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |
      | No further action         | K - No prosecution - Evidential                            | D81    | D80              | Robbery          |                      |

    And I add an action point plan with:
      | field              | value                |
      | Date required by   | 7 days               |
      | Chaser task        | No                   |
      | Related to suspect | All                  |
      | Action             | Orders on Conviction |

    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | False               |

    Then review is submitted successfully
