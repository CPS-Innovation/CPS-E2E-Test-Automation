@regression @CR_UI_S20
Feature: CR-UI-S20 - MC Initial PCD Review for Priority NFS
  As a prosecutor lawyer,
  I want to complete CC initial NFS Compliant day PCD reviews for Early advise
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "priority single defendant single offence"
    And precharge the RED "MC" triage case for "NFS Compliant" PCD review


  @ui @CR_UI_16553 @CR_UI_S20 @EarlyAdviseWithoutActionPlan
  Scenario: Priority NFS Compliant PCD review of test type full code without action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "Priority PCD Review"
    And select test as "Early Advice"
    And I complete the Early Advice pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I select PCD principal offence category as "Robbery"
    And I continue without action plan
    And I submit the charging decision as following
      | Investigative stage        | Create MG3 document |
      | Bail for charging decision | true                |

    Then review is submitted successfully
