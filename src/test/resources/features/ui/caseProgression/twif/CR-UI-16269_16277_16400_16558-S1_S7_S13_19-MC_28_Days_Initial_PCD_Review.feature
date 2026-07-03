@regression @CR_UI_S1_S7_S13_S19
Feature: CR-UI-S1_S7_S13_S19 - MC Initial PCD Review
  As a prosecutor lawyer,
  I want to complete MC initial 28 day PCD reviews for Full Code, Threshold Test and Early Advice
  So that I can submit the review with the correct analysis, monitoring codes, action plan outcome and MG3 document.

  Background: Case Creation
    Given create new case using "CM01" for type "single defendant multiple offence"
    And precharge the "MC" triage case for "28Day" PCD review


  @ui @CR_UI_16400 @CR_UI_S13 @fullCodeWith28DaysActionPlan
  Scenario: 28 days PCD review of test type full code with 28 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Full Code Test"
    And I complete the pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    And I choose DG compliant as Yes
    When I make charging decision as following:
      | decision type     | decision code                   | reason | out come of case | offence category |
      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |

    And I add an action point plan for "28 days" and "Key Witness Details"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | true                |

    Then review is submitted successfully

  @ui @CR_UI_16277 @CR_UI_S7 @thresholdWithActionPlan
  Scenario: 28 days PCD review of test type Threshold with 28 days action plan
    Given I login to case review app
    And Search the case "URN"
    When I start "28 day PCD Review"
    And select test as "Threshold Test"
    And I complete the Threshold Test pre-charge analysis details with:
      | field                       | value                              |
      | Suspect-victim relationship | Partner                            |
      | Global monitoring codes     | Asset Recovery; DA specialist court |

    And I preview pre charge analysis
    When I make charging decision as following:
      | decision type     | decision code                   | reason | out come of case | offence category |
      | No further action | K - No prosecution - Evidential | D81    | D80              | Robbery          |

    And I add an action point plan for "28 days" and "Key Witness Details"
    And I submit the charging decision as following
      | Investigative stage        | Method | Create MG3 document |
      | Bail for charging decision | Area   | true                |

    Then review is submitted successfully


  @ui @CR_UI_16269 @CR_UI_S1 @earlyAdviceWithoutActionPlan
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

    Then review is submitted successfully
