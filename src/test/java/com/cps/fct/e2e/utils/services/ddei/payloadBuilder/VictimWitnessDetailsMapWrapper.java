package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.cps.fct.e2e.model.VictimWitnessDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class VictimWitnessDetailsMapWrapper {
    private Map<String, VictimWitnessDetails> map;

}
