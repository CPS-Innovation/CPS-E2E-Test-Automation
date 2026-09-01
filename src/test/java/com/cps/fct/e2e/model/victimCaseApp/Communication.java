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
    private String PhoneNumber;
    private int CallDirection;
    private String DateOfContact;
    private boolean AbleToInformOnCall;
    private boolean SkippedSMS;
    private boolean SkippedFollowUp;
    private String ReasonForNoFollowUp;
    private String DateTimeSent;
    private boolean Sent;
    private String MessageContent;
    private String GovNotifyAuditGuid;
    private String ToEmailAddress;
    private String SentAt;
    private boolean GovNotifyStatus;
    private String TimeSent;
    private String CreatedBy;
    private String LastModifiedBy;

}
