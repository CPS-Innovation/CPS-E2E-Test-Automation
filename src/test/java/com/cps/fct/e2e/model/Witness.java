package com.cps.fct.e2e.model;

import java.util.List;

public record Witness(List<String> witnessId, List<String> witnessChildId, List<String> witnessExpertId,
                      List<String> witnessPrisonerId, List<String> witnessInterpreterId,
                      List<String> witnessVulnerableId, List<String> witnessPoliceId,
                      List<String> witnessProfessionalId, List<String> witnessIntimidatedId) {

}

