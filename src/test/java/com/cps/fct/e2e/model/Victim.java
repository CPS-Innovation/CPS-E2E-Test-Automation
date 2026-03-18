package com.cps.fct.e2e.model;

import java.util.List;

public record Victim(
                     List<String> victimId, List<String> victimChildId, List<String> victimExpertId,
                     List<String> victimPrisonerId, List<String> victimInterpreterId,
                     List<String> victimVulnerableId, List<String> victimPoliceId,
                     List<String> victimProfessionalId, List<String> victimIntimidatedId) {

}

