package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.FileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.jsonMerge.JsonMergeUtil;
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
        File overridesFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, "dcf meta data");
        File mergedFile = JsonMergeUtil.mergeToTempFile(caseDataFile, overridesFile);

        HttpResponseWrapper responseWrapper = messageService.cm01WithADefendantCharge(mergedFile, messageType, context);
        messageService.persistCaseDetails(responseWrapper, context);
    }

    @And("a {string} is added using {word}")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException {
        String caseType = (String) context.get("caseType");

        File caseDataFile = FileUtils.getValidatedFile(caseType, messageType, caseDataType);
        String metaDataType = JsonMergeUtil.resolveDcfMetaDataType(caseDataType);
        File overridesFile = FileUtils.getValidatedFile(caseType, messageType, metaDataType);
        File mergedFile = JsonMergeUtil.mergeToTempFile(caseDataFile, overridesFile);

        messageService.lmO4AddVictimWitness(mergedFile, caseDataType, context);
    }

}
