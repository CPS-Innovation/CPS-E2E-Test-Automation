package com.cps.fct.e2e.utils.services.messagaingApi;

import com.cps.fct.e2e.model.CaseResponse;
import com.cps.fct.e2e.model.VictimWitnessCMSContact;
import com.cps.fct.e2e.model.VictimWitnessDetails;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.JsonUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForCM01;
import com.cps.fct.e2e.utils.payloadBuilders.dcf.DCFPayloadBuilderForLM04;
import com.cps.fct.e2e.utils.services.BaseService;
import com.jayway.jsonpath.JsonPath;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJson;
import static com.cps.fct.e2e.utils.common.JsonUtils.extractFromJsonNew;

public class DCFMessageService extends BaseService {

    @Inject
    DCFPayloadBuilderForCM01 forCM01;

    @Inject
    DCFPayloadBuilderForLM04 forLM04;

    private VictimWitnessCMSContact buildExpectedOfficerInCaseContact(String requestJson) {
        List<String> givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].Name.GivenName[0].Value");

        List<String> familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].Name.FamilyName.Value");

        List<String> phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        List<String> email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.CaseContacts[?(@.Officer.PoliceOfficerRank == 'PoliceUnit')].ContactDetails.Email");

        return VictimWitnessCMSContact.builder()
                .contactType("OFFICER_IN_CASE")
                .name(familyName.getFirst() + ", " + givenName.getFirst())
                .phone(phone.getFirst())
                .email(email.getFirst())
                .build();
    }

    private VictimWitnessCMSContact buildExpectedDefenceFirmContact(String requestJson) {
        String firmName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Firm");

        String email = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.Email");

        String phone = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.ContactDetails.ContactNumber[0].Number.TelNationalNumber");

        return VictimWitnessCMSContact.builder()
                .contactType("DEFENCE_FIRM")
                .name(firmName)
                .phone(phone)
                .email(email)
                .build();
    }

    private VictimWitnessCMSContact buildExpectedDefenceSolicitorContact(String requestJson) {
        String givenName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.GivenName[0].Value");

        String familyName = JsonPath.read(requestJson,
                "$.PreChargeDecisionRequest.Suspect[0].DefenceSolicitor.Name.FamilyName.Value");
        return VictimWitnessCMSContact.builder()
                .contactType("DEFENCE_SOLICITOR")
                .name(familyName + ", " + givenName)
                .build();
    }

    public HttpResponseWrapper cm01WithADefendantCharge(File caseFile, String messageType, ScenarioContext context) throws IOException {
        String payloadForDefendantAndCharge = Files.readString(caseFile.toPath());
        String modifiedRequestJson = forCM01.generatePayloadWithValues(payloadForDefendantAndCharge, context);

        // Store raw payload for debugging
        context.set("modifiedRequestPayload", modifiedRequestJson);

        VictimWitnessCMSContact expectedOfficerInCaseContact =
                buildExpectedOfficerInCaseContact(modifiedRequestJson);
        context.set("expectedOfficerInCaseContact", expectedOfficerInCaseContact);

        VictimWitnessCMSContact expectedDefenceFirmContact =
                buildExpectedDefenceFirmContact(modifiedRequestJson);
        context.set("expectedDefenceFirmContact", expectedDefenceFirmContact);

        VictimWitnessCMSContact expectedDefenceSolicitorContact =
                buildExpectedDefenceSolicitorContact(modifiedRequestJson);
        context.set("expectedDefenceSolicitorContact", expectedDefenceSolicitorContact);

        return send(modifiedRequestJson, messageType);
    }

    public void lmO4AddVictimWitness(File victimWitness, String messageType, ScenarioContext context) throws IOException {
        String payloadForNewVictimWitness = Files.readString(victimWitness.toPath());
        send(forLM04.generatePayloadWithValues(messageType, payloadForNewVictimWitness, context), messageType);
    }

    private HttpResponseWrapper send(String payload, String messageType) {
        return  service.sendRequest(createDCFMessage(payload, messageType));
    }

    private Map<String, String> messageServiceHeaders() {
        return Map.of(
                "session-id", EnvConfig.get("SESSION_ID"),
                "Content-Type", "application/json",
                "transaction-id", EnvConfig.get("TRANSACTION_ID"));
    }

    private HttpClientBuilder createDCFMessage(String payloadInString, String messageType) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DCF_MESSAGING_API"))
                .endpoint("/inbound-json")
                .addHeaders(messageServiceHeaders())
                .body(payloadInString)
                .method("POST")
                .resourceName(messageType)
                .build();
    }

    public void persistCaseDetails(HttpResponseWrapper response, ScenarioContext context) {
        CaseResponse caseResponse = JsonUtils.fromJson(response.getBody(), CaseResponse.class);
        context.set("caseDetails",caseResponse);
        context.set("caseId", caseResponse.getCaseId());
        context.set("caseUrn", caseResponse.getUrn());
    }
}
