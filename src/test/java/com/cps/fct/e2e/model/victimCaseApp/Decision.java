package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Decision {


    private String DecisionToChargeContextGuid;
    private String NoFurtherActionContextGuid;
    private String DateOfHearing;
    private String LocationOfHearing;
    private Boolean NoFurtherAttempts;
    private Boolean VictimWithdrawn;
    private Boolean VictimNotContacted;
    private String VictimNotContactedReason;
    private String CreatedBy;
    private String LastModifiedBy;
}
