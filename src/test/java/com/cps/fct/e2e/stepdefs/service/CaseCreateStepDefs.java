package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.messagaingApi.CaseCreateService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.assertj.core.api.Assertions;
import org.picocontainer.annotations.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.cps.fct.e2e.utils.services.messagaingApi.assertions.TWIFAssertions.assertTWIFRequest;

public class CaseCreateStepDefs {

    private static final String DEFENDANT_COUNT_CONTEXT_KEY = "defendantCount";

    @Inject
    private CaseService caseService;

    @Inject
    private ScenarioContext context;

    @Inject
    private CaseCreateService messageService;

    @Given("create new case using {string} for type {string}")
    public void createCaseForType(String messageType, String caseDataType) throws IOException, InterruptedException
    {
        File caseDataFile = FileUtils.getValidatedFile( context.get("caseType"), messageType, caseDataType);
        context.set(DEFENDANT_COUNT_CONTEXT_KEY, defendantCount(caseDataFile));
        HttpResponseWrapper responseWrapper = messageService.cm01WithCaseDetails(caseDataFile, messageType, context);
        messageService.getCM01RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("cm01Success"))) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();
            HttpResponseWrapper lastResponseWrapper = null;

            while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.caseDetails(context.get("cm01RequestId"), context);
                lastResponseWrapper = respWrapper;
                messageService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null || caseUrn == null) {
                    Thread.sleep(2000); // wait before retrying
                }

            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId );
                System.out.println("CaseUrn : " + caseUrn );
            } else {
                throw new IllegalStateException("Case creation did not return both caseId and caseUrn within "
                        + timeoutMs + "ms. Last response: "
                        + (lastResponseWrapper == null ? "<none>" : lastResponseWrapper.getBody()));
            }

        }
        else {
            throw new IllegalStateException("Case creation request failed. Response: " + responseWrapper.getBody());
        }

    }

    @And("add {string} using {string} for the case")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException, InterruptedException {
        File caseDataFile = FileUtils.getValidatedFile( context.get("caseType"), messageType, caseDataType);
        HttpResponseWrapper responseWrapper = messageService.lm04AddVictimWitness(caseDataFile, messageType, context);
        messageService.getLM04RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("lm04Success"))) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();
            HttpResponseWrapper lastResponseWrapper = null;

            while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.caseDetails(context.get("lm04RequestId"), context);
                lastResponseWrapper = respWrapper;
                messageService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null || caseUrn == null) {
                    Thread.sleep(3000); // wait before retrying
                }
            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId );
                System.out.println("CaseUrn : " + caseUrn );
            } else {
                throw new IllegalStateException("Victim or Witness creation did not return both caseId and caseUrn within "
                        + timeoutMs + "ms. Last response: "
                        + (lastResponseWrapper == null ? "<none>" : lastResponseWrapper.getBody()));
            }

        }
        else {
            throw new IllegalStateException("Victim or Witness creation request failed. Response: "
                    + responseWrapper.getBody());
        }

    }

    private int defendantCount(File caseDataFile) throws IOException {
        String payloadJson = Files.readString(caseDataFile.toPath());
        List<Object> suspects = JsonPath.read(payloadJson, "$.PreChargeDecisionRequest.Suspect");

        Assertions.assertThat(suspects)
                .as("CM01 suspect list")
                .isNotNull()
                .isNotEmpty();

        return suspects.size();
    }

}
