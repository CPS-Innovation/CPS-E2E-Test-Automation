package com.cps.fct.e2e.utils.services.ddei.payloadBuilder;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PreChargeCaseBuilderTest {

    @Test
    void buildsRedNfsNonCompliantPreChargePayload() throws Exception {
        ZonedDateTime beforePayloadBuild = ZonedDateTime.now(ZoneOffset.UTC).plusMonths(2);

        String payload = PreChargeCaseBuilder.constructPreChargeTriagePriority(
                "MC", "NFS Non Compliant", "12345");

        ZonedDateTime afterPayloadBuild = ZonedDateTime.now(ZoneOffset.UTC).plusMonths(2);

        assertThat(JsonPath.<String>read(payload, "$.decision")).isEqualTo("NFS Non-Compliant");
        assertThat(JsonPath.<String>read(payload, "$.caseType")).isEqualTo("MC");
        assertThat(JsonPath.<String>read(payload, "$.decisionToBeMade")).isEqualTo("Priority");
        String actionPlanDue = JsonPath.read(payload, "$.rejectedDecision.actionPlanDue");
        assertThat(Instant.parse(actionPlanDue))
                .isBetween(beforePayloadBuild.toInstant(), afterPayloadBuild.toInstant());
        assertThat(JsonPath.<List<String>>read(payload, "$.rejectedDecision.rejectionReasons[*].reasonId"))
                .containsExactly("TRR13", "TRR02", "TRR04");
        assertThat(JsonPath.<List<String>>read(payload, "$.rejectedDecision.rejectionReasons[*].reasonTitle"))
                .containsExactly("No/missing Disclosure Schedule", "No MG3", "No DV Checklist");
        assertThat(payload).doesNotContain("\"acceptedDecision\"");
    }
}
