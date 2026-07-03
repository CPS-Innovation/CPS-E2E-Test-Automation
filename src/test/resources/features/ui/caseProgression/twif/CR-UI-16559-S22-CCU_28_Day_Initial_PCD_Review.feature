#@regression @CR_UIS22
#Feature: CR-UI-S22 - CCU Initial PCD Review
#  As a prosecutor lawyer,
#  I want to complete CCU initial 28 day PCD reviews for Early Advice
#  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.
#
#  Background: Case Creation
#    Given create new case using "CM01" for type "multi defendant multiple offence"
#    And precharge the "CCU" triage case for "28Day" PCD review
#
#
#  @ui @CR_UI_16274 @CR_UI_S4 @earlyAdviceWith14DaysActionPlan
#  Scenario: 5 days PCD review of case type CCU and test type Early Advice with 14 days action plan
#    Given I login to case review app
#    And Search the case "URN"
#    When I start "5 day PCD Review"
#    And select test as "Early Advice"
#    And I complete the Early Advice pre-charge analysis details with:
#      | field                       | value                              |
#      | Suspect-victim relationship | Partner                            |
#      | Global monitoring codes     | Asset Recovery; DA specialist court |
#
#    And I select PCD principal offence category as "Robbery"
#    And I add an action point plan with:
#      | field              | value                      |
#      | Date required by   | 14 days                    |
#      | Chaser task        | Yes                        |
#      | Chaser task date   | 1 day before date required |
#      | Related to suspect | All                        |
#      | Action             | Key Exhibits               |
#
#    And I submit the charging decision as following
#      | Investigative stage        | Create MG3 document |
#      | Bail for charging decision | true                |
#
#    Then review is submitted successfully
