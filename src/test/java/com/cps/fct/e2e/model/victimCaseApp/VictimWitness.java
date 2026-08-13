package com.cps.fct.e2e.model.victimCaseApp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VictimWitness {

    private final List<String> victimIds;
    private final List<String> witnessIds;

}
