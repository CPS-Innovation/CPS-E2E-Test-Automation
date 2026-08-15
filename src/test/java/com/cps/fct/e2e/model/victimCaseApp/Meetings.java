package com.cps.fct.e2e.model.victimCaseApp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder

public class Meetings {

    private int MeetingType;
    private int MethodOfOffer;
    private int MeetingOfferAttempt;
    private int MethodOfResponse;
    private int MeetingMethod;
    private int MeetingSource;
    private int LocationType;
    private boolean SpecialNeeds;
    private boolean MeetingOffered;
    private boolean MeetingRequested;
    private boolean RequiresInterpretor;
    private boolean RequiresSupportAttendance;
    private boolean MeetingConducted;
    private boolean NotesSentToOic;
    private boolean NotesSentToVictim;
    private boolean Cancelled;
    private boolean ActionAgreed;
    private boolean ContactForResearch;
    private boolean ChairPerson;
    private boolean AttendedMeeting;
    private String OtherTypeDescription;
    private String MeetingContextGuid;
    private String DateOfOffer;
    private String ReasonForNoOffer;
    private String VictimResponseDate;
    private String VictimResponse;
    private String VictimReasonForDecline;
    private String NoFurtherContactReason;
    private String MeetingDateTime;
    private String NatureOfNeeds;
    private String LocationName;
    private String ReasonNotConducted;
    private String MeetingDuration;
    private String MeetingNotes;
    private String CancellationReason;
    private String CancellationDate;
    private String ProposedActions;
    private String AttendeeName;
    private String AttendeeRole;
    private String MeetingAttendeeGuid;
    private String CreatedBy;
    private String LastModifiedBy;

}
