package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.messagaingApi.CaseCreateService;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.picocontainer.annotations.Inject;
import java.io.File;
import java.io.IOException;

import static com.cps.fct.e2e.utils.services.messagaingApi.assertions.TWIFAssertions.assertTWIFRequest;

public class CaseCreateStepDefs {

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
        HttpResponseWrapper responseWrapper = messageService.cm01WithCaseDetails(caseDataFile, messageType, context);
        messageService.getCM01RequestId(responseWrapper, context);
        if ((Boolean) context.get("cm01Success") == true) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();

            while (caseId == null && caseUrn == null && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.caseDetails(context.get("cm01RequestId"), context);
                messageService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null && caseUrn == null) {
                    Thread.sleep(2000); // wait before retrying
                }

            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId );
                System.out.println("CaseUrn : " + caseUrn );
            } else {
                System.out.println("caseId and caseUrn are null for a long");
            }

        }
        else {
            System.out.println("case creation request failed");
        }

    }

    @And("add {string} using {string} for the case")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException, InterruptedException {
        File caseDataFile = FileUtils.getValidatedFile( context.get("caseType"), messageType, caseDataType);
        HttpResponseWrapper responseWrapper = messageService.lm04AddVictimWitness(caseDataFile, messageType, context);
        messageService.getLM04RequestId(responseWrapper, context);
        if ((Boolean) context.get("lm04Success") == true) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();

            while (caseId == null && caseUrn == null && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.caseDetails(context.get("lm04RequestId"), context);
                messageService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null && caseUrn == null) {
                    Thread.sleep(3000); // wait before retrying
                }
            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId );
                System.out.println("CaseUrn : " + caseUrn );
            } else {
                System.out.println("caseId and caseUrn are null for a long");
            }

        }
        else {
            System.out.println("Victim or Witness creation request failed");
        }

    }

}
