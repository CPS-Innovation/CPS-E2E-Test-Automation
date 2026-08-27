package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MeetingLogged {

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
    private boolean MeetingConducted;
    private String ReasonNotConducted;
    private String MeetingDuration;
    private boolean ActionAgreed;
    private String ProposedActions;
    private int ContactForResearch;
    private String ReasonForChange;
    private Boolean NotesSentToOic;
    private Boolean NotesSentToVictim;
    private String LastModifiedBy;
    private Boolean Cancelled;

}
