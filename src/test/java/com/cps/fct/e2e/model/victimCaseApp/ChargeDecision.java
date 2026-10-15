package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ChargeDecision {

    private String DecisionToChargeContextGuid;
    private String NoFurtherActionContextGuid;
    private String CreatedBy;
    private String LastModifiedBy;

}
