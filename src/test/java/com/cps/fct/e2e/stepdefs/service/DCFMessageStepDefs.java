package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.messagaingApi.DCFMessageService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;

public class DCFMessageStepDefs {

    @Inject
    private CaseService caseService;

    @Inject
    private ScenarioContext context;

    @Inject
    private DCFMessageService messageService;

    @Given("create case {word} for type {string}")
    public void createCaseUsing(String messageType, String caseDataType) throws IOException, InterruptedException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        HttpResponseWrapper responseWrapper = messageService.cm01WithADefendantCharge(caseDataFile, messageType, context);
        messageService.persistCM01RequestId(responseWrapper, context);
        if ((Boolean) context.get("cm01Success") == true) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();

            while (caseId == null && caseUrn == null && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = messageService.cm01DcfCaseDetails(context.get("dcfCm01RequestId"), context);
                messageService.persistDcfCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null && caseUrn == null) {
                    Thread.sleep(2000); // wait before retrying
                }
            }

            if (caseId != null && caseUrn != null) {
                System.out.println("DCF CaseId : " + caseId );
                System.out.println("DCF CaseUrn : " + caseUrn );
            } else {
                System.out.println("caseId and caseUrn are null for a long");
            }

        }
        else {
            System.out.println("DCF case creation request failed");
        }
    }

    @And("a {string} is added using {word}")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        messageService.lmO4AddVictimWitness(caseDataFile, caseDataType, context);




    }

    @And("a {string} is added with details using {word}")
    public void addNewVictimOrWitnessWithDetails(String caseDataType, String messageType) throws IOException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        messageService.lmO4AddVictimWitness(caseDataFile, caseDataType, context);
    }
}
