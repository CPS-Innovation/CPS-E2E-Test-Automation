package com.cps.fct.e2e.stepDefinition.api;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseReviewService;
import com.cps.fct.e2e.utils.services.caseCreation.CaseCreateService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.assertj.core.api.Assertions;
import org.picocontainer.annotations.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CaseCreateApiStepDefinition {

    private static final String DEFENDANT_COUNT_CONTEXT_KEY = "defendantCount";
    private static final String CASE_DATA_FILE_NAME_CONTEXT_KEY = "caseDataFileName";
    private static final long CASE_MESSAGE_TIMEOUT_MS = 120000;
    private static final long CJ05_TIMEOUT_MS = 300000;

    @Inject
    private CaseReviewService caseReviewService;

    @Inject
    private ScenarioContext context;

    @Inject
    private CaseCreateService messageService;

    @Given("create new case using {string} for type {string}")
    public void createCaseForType(String messageType, String caseDataType) throws IOException, InterruptedException
    {
        File caseDataFile = FileUtils.getValidatedFile( context.get("caseType"), messageType, caseDataType);
        context.set(CASE_DATA_FILE_NAME_CONTEXT_KEY, caseDataFile.getName());
        context.set(DEFENDANT_COUNT_CONTEXT_KEY, defendantCount(caseDataFile));
        HttpResponseWrapper responseWrapper = messageService.cm01WithCaseDetails(caseDataFile, messageType, context);
        System.out.println(responseWrapper);
        messageService.getCM01RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("cm01Success"))) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = CASE_MESSAGE_TIMEOUT_MS;
            long startTime = System.currentTimeMillis();
            HttpResponseWrapper lastResponseWrapper = null;

            while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.caseDetails(context.get("cm01RequestId"), context);
                lastResponseWrapper = respWrapper;
                messageService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null || caseUrn == null) {
                    Thread.sleep(3000);
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
        if (!"LM04".equalsIgnoreCase(messageType)) {
            addCaseMessage(caseDataFile, messageType);
            return;
        }

        HttpResponseWrapper responseWrapper = messageService.lm04AddVictimWitness(caseDataFile, messageType, context);
        messageService.getLM04RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("lm04Success"))) {
            waitForCaseIdAndUrn(messageType, context.get("lm04RequestId"));
            return;
        }
        else {
            throw new IllegalStateException("Victim or Witness creation request failed. Response: "
                    + responseWrapper.getBody());
        }

    }

    private void addCaseMessage(File caseDataFile, String messageType) throws IOException, InterruptedException {
        HttpResponseWrapper responseWrapper = messageService.caseMessage(caseDataFile, messageType, context);
        com.cps.fct.e2e.model.caseCreation.CaseResponse caseResponse =
                com.cps.fct.e2e.utils.common.JsonUtils.fromJson(
                        responseWrapper.getBody(),
                        com.cps.fct.e2e.model.caseCreation.CaseResponse.class);

        if (!caseResponse.isSuccess()) {
            throw new IllegalStateException(messageType + " request failed. Response: " + responseWrapper.getBody());
        }

        String requestId = caseResponse.getRequestId();
        context.set(messageType.toLowerCase() + "RequestId", requestId);
        if ("CJ05".equalsIgnoreCase(messageType)) {
            waitForCaseIdAndUrn(messageType, requestId);
            return;
        }

        waitForCaseIdAndUrn(messageType, requestId);
    }

    private void waitForCaseIdAndUrn(String messageType, String requestId) throws InterruptedException {
        String caseId = null;
        String caseUrn = null;
        long timeoutMs = "CJ05".equalsIgnoreCase(messageType) ? CJ05_TIMEOUT_MS : CASE_MESSAGE_TIMEOUT_MS;
        long startTime = System.currentTimeMillis();
        HttpResponseWrapper lastResponseWrapper = null;

        while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
            HttpResponseWrapper responseWrapper = messageService.caseDetails(requestId, context);
            lastResponseWrapper = responseWrapper;
            messageService.persistCaseDetails(responseWrapper, context);
            caseId = context.get("caseId");
            caseUrn = context.get("caseUrn");

            if (caseId == null || caseUrn == null) {
                Thread.sleep(3000);
            }
        }

        if (caseId != null && caseUrn != null) {
            System.out.println("CaseId : " + caseId);
            System.out.println("CaseUrn : " + caseUrn);
            return;
        }

        throw new IllegalStateException(messageType + " did not return both caseId and caseUrn within "
                + timeoutMs + "ms. Last response: "
                + (lastResponseWrapper == null ? "<none>" : lastResponseWrapper.getBody()));
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
