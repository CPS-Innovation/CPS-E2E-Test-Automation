package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Communication {

    private int JourneyType;
    private int CommunicationType;
    private String OtherCommunicationType;
    private int Direction;
    private String DateOfCommunication;
    private String TimeOfCommunication;
    private String PurposeOfCommunication;
    private String Notes;
    private String PersonName;
    private String PersonRole;
    private String CreatedBy;
    private String LastModifiedBy;

}
