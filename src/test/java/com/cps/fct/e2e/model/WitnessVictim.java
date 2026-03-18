package com.cps.fct.e2e.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WitnessVictim {

    private final List<String> victimIds;
    private final List<String> witnessIds;

}
