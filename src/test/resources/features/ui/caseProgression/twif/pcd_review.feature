@regression
Feature: Review TWIF Case and Apply Charges
  As a prosecutor lawyer,
  I want to review a case in the Case review
  So that I can update the case progression and apply relevant charges.

 @ui
  Scenario: Demo test for 28 days PCD review of test type full code
  Given create TWIF case "CM01" for type "single defendant with charge"
  And precharge the triage case for 28 days PCD review
  And login to case review app
  And Search the case
  When I start "28 day PCD Review"
  And select test as "Full Code Test"
  And I complete the pre-charge analysis details with:
    | field                       | value                              |
    | Suspect-victim relationship | Partner                            |
    | Global monitoring codes     | Asset Recovery; DA specialist court |
  And I preview pre charge analysis
  And I choose DG compliant as Yes
  When I make charging decision as following:
    | decision type| decision code| reason | out come of case| offence category |
    |No further action|K - No prosecution - Evidential |D81 |D80| Robbery|
  And I continue without action plan
  And I submit the charging decision as following
    | Investigative stage        | Method | Create MG3 document |
    | Bail for charging decision | Area   | true                |
  Then review is submitted successfully












