//package com.cps.fct.e2e.stepdefs.service;
//
//import com.cps.fct.e2e.model.caseCreation.*;
//import com.cps.fct.e2e.model.caseReviewApp.*;
//import com.cps.fct.e2e.utils.common.ScenarioContext;
//import com.cps.fct.e2e.utils.services.ddei.CaseReviewService;
//import com.cps.fct.e2e.utils.services.ddei.CommonService;
////import com.fasterxml.jackson.core.JsonProcessingException;
//import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
////import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.JsonProcessingException;
//import org.picocontainer.annotations.Inject;
//
//import java.util.List;
//
//public class CaseReviewStepDefinition  {
//
//    @Inject
//    private CaseReviewService caseReviewService;
//
//    @Inject
//    private CommonService service;
//
//    @Inject
//    private ScenarioContext context;
//
//
////    @Given("case URN exists in CMS")
////    public void caseURNExistsInCMS() {
////        List<Case> caseDetails = caseReviewService.listCaseDetails(context.get("caseUrn"));
////        context.set("caseDetails", caseDetails);
////    }
//
//    @And("precharge the {string} triage case for {string} PCD review")
//    public void prechargeTriageCaseForPcdReview(String caseType, String decisionToBeMade) throws JsonProcessingException {
//        service.createCmsAuthToken(context);
//        caseReviewService.prechargeTriageCaseAccepted(
//                requiredContextValue("caseUrn"),
//                requiredContextValue("caseId"),
//                caseType,
//                decisionToBeMade);
//    }
//
//    // RED (Priority) triage. The decisionToBeMade is fixed to Priority; the triage decision
//    // (e.g. NFS Compliant) is passed in and recorded in the payload's decision field, with
//    // rejectedDecision left null/null because the case is accepted rather than rejected.
//    @And("precharge the RED {string} triage case for {string} PCD review")
//    public void prechargeRedTriageCaseForPcdReview(String caseType, String triageDecision) throws JsonProcessingException {
//        service.createCmsAuthToken(context);
//        caseReviewService.prechargeTriageCasePriority(
//                requiredContextValue("caseUrn"),
//                requiredContextValue("caseId"),
//                caseType,
//                triageDecision);
//    }
//
//    private String requiredContextValue(String key) {
//        String value = context.getAsString(key);
//        if (value == null || value.isBlank()) {
//            throw new IllegalStateException("No value found in scenario context for key: " + key);
//        }
//        return value;
//    }
//
//}
