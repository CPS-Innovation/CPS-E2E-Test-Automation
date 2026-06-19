@regression @VCA_API_S10

Feature: VCA-API-S10 - Meetings are not being offered with reasons for certain meeting type for victims or witnesses
  As a Victim Liaison Officer
  I want to record the meetings is not being offered with reasons for following type:-
    1. CPS pre-trial meeting
    2. Inform victim about charging decision
    3. Stopped or Substantially altered charge (VCL Scheme)
    4. Victims' Right to Review (VRR)
    5. Victim complaint
    6. Other
  Verify that not offered meeting details are recorded

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @ptmNotOffered
  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
    Given witness and victim details are available
    And the "victimId" is onboarded to VCA
    When the "CPS pre-trial meeting" is not offered to "victimId" in VCA
    Then the "CPS pre-trial meeting" details for "victimId" is verified in VCA

#  @informVictimChargingDecision
#  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
#    Given witness and victim details are available
#    And the "victimId" is onboarded to VCA
#    When the "Inform victim about charging decision" is not offered to "victimId" in VCA
#    Then the "Inform victim about charging decision" details for "victimId" is verified in VCA
#
#  @VCLScheme
#  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
#    Given witness and victim details are available
#    When the "Stopped or Substantially altered charge" is not offered to "victimId" in VCA
#    Then the "Stopped or Substantially altered charge" details for "victimId" is verified in VCA
#
#  @VictimsRightToReview
#  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
#    Given witness and victim details are available
#    When the "Victims Right to Review" is not offered to "victimId" in VCA
#    Then the "Victims Right to Review" details for "victimId" is verified in VCA
#
#  @Complaints
#  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
#    Given witness and victim details are available
#    When the "Victim complaint" is not offered to "victimId" in VCA
#    Then the "Victim complaint" details for "victimId" is verified in VCA
#
#  @Other
#  Scenario: CPS pre-trial meeting is not being offered with reasons for victims or witnesses
#    Given witness and victim details are available
#    When the "Other" is not offered to "victimId" in VCA
#    Then the "Other" details for "victimId" is verified in VCA