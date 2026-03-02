package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.FileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.jsonMerge.JsonMergeUtil;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.messagaingApi.DCFMessageService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


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
//        String mergedContent = Files.readString(mergedFile.toPath());
//        System.out.println("Merged JSON:\n" + mergedContent);

        HttpResponseWrapper responseWrapper = messageService.cm01WithADefendantCharge(mergedFile, messageType, context);
        messageService.persistCaseDetails(responseWrapper, context);
    }

    @And("a {string} is added using {word}")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException {
//        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
//        String metaDataType = JsonMergeUtil.resolveDcfMetaDataType(caseDataType);
//        File overridesFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, metaDataType);
//        File mergedFile = JsonMergeUtil.mergeToTempFile(caseDataFile, overridesFile);
//        messageService.lmO4AddVictimWitness(mergedFile, caseDataType, context);

        String caseType = (String) context.get("caseType");

        System.out.println("caseType     = " + caseType);
        System.out.println("messageType  = " + messageType);
        System.out.println("caseDataType = " + caseDataType);

        File caseDataFile = FileUtils.getValidatedFile(caseType, messageType, caseDataType);
        System.out.println("base file path     = " + caseDataFile.getAbsolutePath());

        String metaDataType = JsonMergeUtil.resolveDcfMetaDataType(caseDataType);
        System.out.println("metaDataType       = " + metaDataType);

        File overridesFile = FileUtils.getValidatedFile(caseType, messageType, metaDataType);
        System.out.println("override file path = " + overridesFile.getAbsolutePath());

        File mergedFile = JsonMergeUtil.mergeToTempFile(caseDataFile, overridesFile);

        messageService.lmO4AddVictimWitness(mergedFile, caseDataType, context);
    }

}
