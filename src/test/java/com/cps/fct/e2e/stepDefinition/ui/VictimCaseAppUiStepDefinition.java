package com.cps.fct.e2e.stepDefinition.ui;

import com.cps.fct.e2e.pages.PageObjects;
import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.FakerUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import com.cps.fct.e2e.utils.services.ddei.CaseReviewService;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.picocontainer.annotations.Inject;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VictimCaseAppUiStepDefinition {

    @Inject private CaseReviewService caseReviewService;
    @Inject private CommonService service;
    @Inject private PageObjects pages;
    @Inject private ScenarioContext context;
    private static final String CPS_USER_KEY = "CPS_USER";
    private static final String PASSWORD_KEY = "PASSWORD";


    public VictimCaseAppUiStepDefinition() {
    }

    @Given("VLO login to victim case application")
    public void vloLogIntoVca() throws InterruptedException {

        pages.vcaLoginPage.loginIntoVca(vcaUsername(),vcaPassword());

    }

    private String vcaUsername() {
        String envSuffix = requiredContextValue("envSuffix");
        return requiredEnvValue(CPS_USER_KEY) + envSuffix;
    }

    private String vcaPassword() {

        return SecurePassCode.decode(requiredEnvValue(PASSWORD_KEY));
    }

    private String requiredContextValue(String key) {
        String value = context.getAsString(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("No value found in scenario context for key: " + key);
        }
        return value;
    }


    private String requiredEnvValue(String key) {
        String value = EnvConfig.getEnv(key);
        if (isBlank(value)) {
            throw new IllegalStateException("No value found in environment for key: " + key);
        }
        return value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }


























}
