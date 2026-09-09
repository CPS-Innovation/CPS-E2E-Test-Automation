@regression @vca_api_regression @VCA_API_S32 @VCA_API_S33 @VCA_API_S42 @VCA_API_S43

Feature: VCA-API-S26 - Log charge decision communication where first call attempt was un-successful and SMS details sent
  and Second call attempt was un-successful follow up communication is sent in email or post for victims in Victim Case Application.
  As a Victim Liaison Officer
  I want to log charge decision communication for victim.
  where first call attempt was un-successful and SMS is sent
  and second call attempt was un-successful
  then follow up communication is sent in email or post
  verify that charge decision communication is logged.

  Background: Create cases with single defendant with multi charge with victim and witness
    Given create new case using "CM01" for type "single defendant multiple offence"
    And add "victim" using "LM04" for the case

  @FCUnsuccessNoSmsSCUnsuccess
  Scenario: Log charge decision communication where first call attempt was un-successful with No Sms sent,
  and Second call attempt was un-successful
  and follow up communication is sent for victim
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the first telephone call attempt is un-successful to inform victim with following details for "victim" in VCA
      | journeyType                            | informVictim | callDirection | notes                        | smsSent |
      | Inform of a no further action decision | No           | Inbound       | First Telephone call - Notes | No      |
      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge
    And the second telephone call attempt is un-successful to inform victim with following details for "victim" in VCA
      | journeyType                            | informVictim | callDirection | notes                        |
      | Inform of a no further action decision | No           | Inbound       | First Telephone call - Notes |
    And follow up as below for "victim" in VCA
      | journeyType                            | followUpMethod | notes         |
      | Inform of a no further action decision | Email          | Email - Notes |
      #  followUpMethod :- Email or Post
    Then verify the charge decision communication for "victim" in VCA

  @FCUnsuccessSmsSentSCUnsuccess
  Scenario: Log charge decision communication where first call attempt was un-successful with Sms sent and follow up communication is sent for victim
    Given victim details are available in VCA
    And the "victim" is onboarded as "Universal" service lead in VCA
    And the Victim liaison officer is assigned to "victim" in VCA
    When the first telephone call attempt is un-successful to inform victim with following details for "victim" in VCA
      | journeyType                            | informVictim | callDirection | notes                  | smsSent |
      | Inform of a no further action decision | No           | Inbound       | Telephone call - Notes | Yes     |
      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge
    And the second telephone call attempt is un-successful to inform victim with following details for "victim" in VCA
      | journeyType                            | informVictim | callDirection | notes                        |
      | Inform of a no further action decision | No           | Inbound       | First Telephone call - Notes |
      #  journeyType :- Inform of a no further action decision or #  Inform of a decision to charge
    And follow up as below for "victim" in VCA
      | journeyType                            | followUpMethod | notes        |
      | Inform of a no further action decision | Post           | Post - Notes |
      #  followUpMethod :- Email or Post
    Then verify the charge decision communication for "victim" in VCA
