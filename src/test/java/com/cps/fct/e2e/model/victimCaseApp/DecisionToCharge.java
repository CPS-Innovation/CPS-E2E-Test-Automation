package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DecisionToCharge {

    private String DecisionToChargeContextGuid;
    private String CreatedBy;
    private String LastModifiedBy;

}
