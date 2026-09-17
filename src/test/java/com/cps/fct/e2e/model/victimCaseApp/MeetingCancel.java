package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MeetingCancel {

    private int MeetingType;
    private int MeetingMethod;
    private String MeetingContextGuid;
    private int MeetingSource;
    private String MeetingDateTime;
    private int LocationType;
    private String LocationName;
    private boolean SpecialNeeds;
    private boolean RequiresInterpretor;
    private boolean RequiresSupportAttendance;
    private String NatureOfNeeds;
    private String OtherTypeDescription;
    private String LastModifiedBy;
    private boolean Cancelled;
    private String CancellationReason;
    private String CancellationDate;
}
