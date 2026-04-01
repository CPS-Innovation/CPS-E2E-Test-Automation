package com.cps.fct.e2e.stepdefs.service;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.services.ddei.CaseService;
import com.cps.fct.e2e.utils.services.messagaingApi.TWIFMessageService;
import io.cucumber.java.en.Given;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;

import static com.cps.fct.e2e.utils.services.messagaingApi.assertions.TWIFAssertions.assertTWIFRequest;

public class TWIFMessageStepDefs {

    @Inject
    private CaseService caseService;

    @Inject
    private ScenarioContext context;

    @Inject
    private TWIFMessageService messageService;

    @Given("create TWIF case {string} for type {string}")
    public void createTWIFCaseForType(String messageType, String caseDataType) throws IOException {
        File caseDataFile = FileUtils.getValidatedFile(
                context.get("caseType"),
                messageType,
                caseDataType
        );
        messageService.cm01WithADefendantCharge(caseDataFile, messageType, context);
        assertTWIFRequest(messageType);
        caseService.retryUntilCaseIsInCMS(context);
    }
}
