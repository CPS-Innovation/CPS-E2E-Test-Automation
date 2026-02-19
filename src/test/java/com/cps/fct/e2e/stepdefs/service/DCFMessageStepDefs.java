package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.FileMapping.FileUtils;
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
    public void createCaseUsing(String messageType, String caseDataType) throws IOException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
//        File caseDataFile = FileUtils.getValidatedFile(
//                context.get("caseType"),   // <-- currently null
//                messageType,
//                caseDataType
//        );
        HttpResponseWrapper responseWrapper = messageService.cm01WithADefendantCharge(caseDataFile, messageType, context);
        messageService.persistCaseDetails(responseWrapper, context);
    }

    @And("a {string} is added using {word}")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        messageService.lmO4AddVictimWitness(caseDataFile, caseDataType, context);
    }

}
