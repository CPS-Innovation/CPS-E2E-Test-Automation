@regression @caseReview @ui
Feature: CR-UI-S23 - MC Initial PCD Review for Priority NFS Complaint
  As a prosecutor lawyer,
  I want to complete MC initial 28 day PCD reviews for Full Code test
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "priority multi suspect single offence"
    And precharge the RED "MC" triage case for "NFS Compliant" PCD review


  @CR_UI_16555 @CR_UI_S23 @fullTestCodeWithoutActionPlan
  Scenario: 28 days PCD review of case type MC and test type Full Code without action plan
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
      | decision type | decision code                          | reason | out come of case | offence category | charge code decision |
      | Charge        | A - Charge and request evidential file | D81    | D80              | Robbery          | Accept               |
      | Charge        | A - Charge and request evidential file | D81    | D80              | Robbery          | Accept               |
      | Charge        | A - Charge and request evidential file | D81    | D80              | Robbery          | Accept               |
      | Charge        | A - Charge and request evidential file | D81    | D80              | Robbery          | Accept               |

    And I continue without action plan
    And I submit the charging decision as following
      | Investigative stage        | Method |
      | Bail for charging decision | Area   |

    Then review is submitted successfully
