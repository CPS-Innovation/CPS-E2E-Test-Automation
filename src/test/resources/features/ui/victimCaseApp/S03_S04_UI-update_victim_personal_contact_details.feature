@regression @ui_vca_regression @VCA_UI_S3 @VCA_UI_S4

Feature: VCA-UI-S1_S2:- UI Flow - Update existing personal and contact details for victims or victimWitness person and verify wm01u message
  As a Victim Liaison Officer
  I want to update personal and contact details for victim or victimWitness person
  Update information is reflected in CMS and VCA database
  Verify wm01u message is sent to police system

  Background: Create cases with single defendant with multiple charge with all category types of victims and victims witness
  with personal and contact details.

    Given create new case using "CM01" for type "single suspect multiple offence"
    And add "victim witness" using "LM04" for the case
    And the "victimWitness" person details are available in CMS

  @ui @updatePersonalAndContactDetailsForVictim
  Scenario: Update victim Forename, Surname, Title, Date of birth, Gender, Address, Telephone number, Email address,
  Preferred name, Preferred correspondence language, Preferred method of contact for CPS, Preferred contact times,
  Victim representative details, Power of attorney details
  and verify that newly added details are sent in wm01u message. (FCT2-15283 & FCT2-15284)

    Given VLO login to victim case application
    And "victimWitness" is searched using case reference
    And the "victimWitness" is onboarded to "Universal" service lead
#      service lead options = Universal, Enhanced, Rasso, Not aligned
    And select "No task at this time" for next task for the "victimWitness"
    And verify that "victimWitness" is onboarded
    And the "Murali" is assigned as VLO to "victimWitness"
    And verify that Vlo "Murali" is assigned to "victimWitness"
    And the following "victimWitness" personal details are added in VCA
      | vcaField                            | value       |
      | Preferred name                      |             |
      | Preferred method of contact for CPS | Home phone  |
      #Home phone #Mobile #Email #Post #Via police #Via Independant Sexual Violence Adviser (ISVA)
      | Preferred contact times             | 10am to 4pm |
      | Victim representative details       |             |
      | Power of attorney details           |             |
    Then the "victimWitness" forename and surname are updated with "Justification Text" to CMS
    Then the following "victimWitness" personal details are updated to CMS
      | cmsField         | justification        |
      | Date of birth    | Update Date of Birth |
#      | Gender           | Update Gender                 |
#      | Address          | Update Address       |
      | Telephone number | Update Telephone     |
      | Email address    | Update email address |
    Then the following "victimWitness" personal details are updated in VCA
      | vcaField                            | value       |
      | Preferred name                      |             |
      | Preferred correspondence language   | Welsh       |
      | Preferred method of contact for CPS | Mobile      |
      #Home phone #Mobile #Email #Post #Via police #Via Independant Sexual Violence Adviser (ISVA)
      | Preferred contact times             | 11am to 3pm |
      | Victim representative details       |             |
      | Power of attorney details           |             |