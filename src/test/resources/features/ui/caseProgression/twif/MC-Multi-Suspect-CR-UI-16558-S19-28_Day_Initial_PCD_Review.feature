@regression @caseReview @ui
Feature: CR-UI-S19 - MC Initial PCD Review for multi suspect
  As a prosecutor lawyer,
  I want to complete MC initial 28 day PCD reviews for Early Advice
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "multi suspect multi offence"
    And precharge the "MC" triage case for "28Day" PCD review


  @CR_UI_16558 @CR_UI_S19 @fullCodeWithoutActionPlan
  Scenario: 28 days PCD review of test type Early Advice without action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
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

