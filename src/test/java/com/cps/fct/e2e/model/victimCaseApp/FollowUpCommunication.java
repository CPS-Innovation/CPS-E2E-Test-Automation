package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FollowUpCommunication {

    private int JourneyType;
    private String Notes;
    private String SentAt;
    private String TimeSent;
    private String CreatedBy;
    private String LastModifiedBy;

}
