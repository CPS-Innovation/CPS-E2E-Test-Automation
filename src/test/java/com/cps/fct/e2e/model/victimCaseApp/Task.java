package com.cps.fct.e2e.model.victimCaseApp;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Task {

    private int Assignee;
    private String DueDate;
    private int Task;
    private String Action;
    private String Notes;
    private int OrderNumber;
    private String Context;
    private String CreatedBy;
    private String LastModifiedBy;

}
