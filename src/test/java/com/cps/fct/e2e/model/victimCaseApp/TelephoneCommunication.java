package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TelephoneCommunication {

    private int JourneyType;
    private String PhoneNumber;
    private int CallDirection;
    private String DateOfContact;
    private boolean AbleToInformOnCall;
    private String Notes;
    private boolean SkippedSMS;
    private boolean SkippedFollowUp;
    private String ReasonForNoFollowUp;
    private int AttemptOrder;
    private String DateTimeSent;
    private boolean Sent;
    private String MessageContent;
    private String CreatedBy;
    private String LastModifiedBy;

}
