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
    private boolean NoFurtherAttempts;
    private boolean VictimWithdrawn;
    private boolean VictimNotContacted;
    private String VictimNotContactedReason;
    private String CreatedBy;
    private String LastModifiedBy;
}
