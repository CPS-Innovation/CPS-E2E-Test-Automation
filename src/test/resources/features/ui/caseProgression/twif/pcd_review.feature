Feature: Review TWIF Case and Apply Charges
  As a prosecutor lawyer,
  I want to review a case in the Case review
  So that I can update the case progression and apply relevant charges.

  @TWIF @ui
  Scenario: Demo test for 28 days PCD review of test type full code
  Given create TWIF case "CM01" for type "single defendant with charge"
  And precharge the triage case for 28 days PCD review
  And login to case review app
  And Search the case
  When I start "28 day PCD Review"
  And select test as "Full Code Test"
  And the case headline is entered
  And the evidential analysis is entered
  And I write the "Public interest assessment"
  And I write the "Disclosure management"
  And I write the "Allocation" and choose NGAP option has Yes
  And I write the "Victim and witness needs"
  And I write the "Trial and sentencing preparation"
  And I choose "Not at this time" in Human Rights
  And I write the "Advocate and operational delivery instructions"
  And I choose Global monitoring codes as
    |Asset Recovery  |
    |DA specialist court  |
  And I preview pre charge analysis
  And I choose DG compliant as Yes
  When I make charging decision as following:
    | decision type| decision code| reason | out come of case| offence category |
    |No further action|K - No prosecution - Evidential |D81 |D80| Robbery|
  And I continue without action plan
  And I submit the charging decision as following
    | Investigative stage        | Method |
    | Bail for charging decision | Area   |
  Then review is submitted successfully












