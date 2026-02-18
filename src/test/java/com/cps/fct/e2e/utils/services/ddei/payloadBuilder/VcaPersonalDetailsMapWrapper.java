package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


@Getter
@AllArgsConstructor
public class VcaPersonalDetailsMapWrapper {
    private Map<String, VcaPersonalDetails> map;
}
