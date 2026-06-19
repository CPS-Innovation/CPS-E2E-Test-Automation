package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder

public class VictimMeetingDetails {

    private int MeetingType;
    private String OtherTypeDescription;
    private String MeetingContextGuid;
    private boolean MeetingOffered;
    private boolean MeetingRequested;
    private int MethodOfOffer;
    private String DateOfOffer;
    private String ReasonForNoOffer;
    private String CreatedBy;

}
