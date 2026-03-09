package com.cps.fct.e2e.model;

import java.util.List;

public record WitnessVictim(List<String> witnessId, List<String> witnessChildId, List<String> witnessExpertId,
                            List<String> witnessPrisonerId, List<String> witnessInterpreterId,
                            List<String> witnessVulnerableId, List<String> witnessPoliceId,
                            List<String> witnessProfessionalId, List<String> witnessIntimidatedId,
                            List<String> victimId, List<String> victimChildId, List<String> victimExpertId,
                            List<String> victimPrisonerId, List<String> victimInterpreterId,
                            List<String> victimVulnerableId, List<String> victimPoliceId,
                            List<String> victimProfessionalId, List<String> victimIntimidatedId) {
}
