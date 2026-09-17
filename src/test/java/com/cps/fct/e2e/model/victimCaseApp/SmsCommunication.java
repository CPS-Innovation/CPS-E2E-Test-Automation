package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SmsCommunication {

    private int JourneyType;
    private String DateTimeSent;
    private boolean Sent;
    private String MessageContent;
    private String CreatedBy;
    private String LastModifiedBy;

}
