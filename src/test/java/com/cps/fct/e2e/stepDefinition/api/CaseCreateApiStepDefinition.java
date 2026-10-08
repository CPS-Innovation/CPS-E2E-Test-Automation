package com.cps.fct.e2e.stepDefinition.api;

import com.cps.fct.e2e.utils.fileMapping.FileUtils;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.ddei.CaseReviewService;
import com.cps.fct.e2e.utils.services.caseCreation.CaseCreateService;
import com.cps.fct.e2e.utils.services.ddei.CommonService;
import com.cps.fct.e2e.utils.services.ddei.VictimService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.picocontainer.annotations.Inject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CaseCreateApiStepDefinition {

    private static final String DEFENDANT_COUNT_CONTEXT_KEY = "defendantCount";
    private static final String CASE_DATA_FILE_NAME_CONTEXT_KEY = "caseDataFileName";

    @Inject
    private CaseReviewService caseReviewService;

    @Inject
    private ScenarioContext context;

    @Inject
    private CaseCreateService caseCreateService;

    @Inject
    private CommonService service;

    @Inject
    private VictimService victimService;

    @Given("create new case using {string} for type {string}")
    public void createCaseForType(String messageType, String caseDataType) throws IOException, InterruptedException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        context.set(CASE_DATA_FILE_NAME_CONTEXT_KEY, caseDataFile.getName());
        context.set(DEFENDANT_COUNT_CONTEXT_KEY, defendantCount(caseDataFile));
        HttpResponseWrapper responseWrapper = caseCreateService.cm01WithCaseDetails(caseDataFile, messageType, context);
        caseCreateService.getCM01RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("cm01Success"))) {
            String caseId = null;
            String caseUrn = null;
            String status = "PENDING";
            String errorMessage = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();
            HttpResponseWrapper lastResponseWrapper = null;

            while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = caseCreateService.caseDetails(context.get("cm01RequestId"), context);
                lastResponseWrapper = respWrapper;
                caseCreateService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");
                status = context.get("status");
                errorMessage = context.get("errorMessage");

                if (caseId == null || caseUrn == null) {
                    Thread.sleep(2000); // wait before retrying
                } else if (Objects.equals(status, "FAILURE")) {
                    System.out.println("Case Creation Request ID failed for:" + errorMessage);
                }
            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId);
                System.out.println("CaseUrn : " + caseUrn);
            } else {
                throw new IllegalStateException("Case creation did not return both caseId and caseUrn within "
                        + timeoutMs + "ms. Last response: "
                        + (lastResponseWrapper == null ? "<none>" : lastResponseWrapper.getBody()));
            }

        } else {
            throw new IllegalStateException("Case creation request failed. Response: " + responseWrapper.getBody());
        }

    }

    @And("add {string} using {string} for the case")
    public void addNewVictimOrWitness(String caseDataType, String messageType) throws IOException, InterruptedException {
        File caseDataFile = FileUtils.getValidatedFile(context.get("caseType"), messageType, caseDataType);
        HttpResponseWrapper responseWrapper = caseCreateService.lm04AddVictimWitness(caseDataFile, messageType, context);
        caseCreateService.getLM04RequestId(responseWrapper, context);
        if (Boolean.TRUE.equals(context.get("lm04Success"))) {
            String caseId = null;
            String caseUrn = null;
            long timeoutMs = 90000;
            long startTime = System.currentTimeMillis();
            HttpResponseWrapper lastResponseWrapper = null;

            while ((caseId == null || caseUrn == null) && System.currentTimeMillis() - startTime < timeoutMs) {
                HttpResponseWrapper respWrapper = caseCreateService.caseDetails(context.get("lm04RequestId"), context);
                lastResponseWrapper = respWrapper;
                caseCreateService.persistCaseDetails(respWrapper, context);
                caseId = context.get("caseId");
                caseUrn = context.get("caseUrn");

                if (caseId == null || caseUrn == null) {
                    Thread.sleep(2000); // wait before retrying
                }
            }

            if (caseId != null && caseUrn != null) {
                System.out.println("CaseId : " + caseId);
                System.out.println("CaseUrn : " + caseUrn);
            } else {
                throw new IllegalStateException("Victim or Witness creation did not return both caseId and caseUrn within "
                        + timeoutMs + "ms. Last response: "
                        + (lastResponseWrapper == null ? "<none>" : lastResponseWrapper.getBody()));
            }

        } else {
            throw new IllegalStateException("Victim or Witness creation request failed. Response: "
                    + responseWrapper.getBody());
        }

    }

    @When("the {string} details are available in CMS")
    public void lm04DetailsInCms(String personType) {
        service.createCmsAuthToken(context);

        Map<String, String> personTypePersonNameMap = new HashMap<>();
        context.set("personTypePersonNameMap", personTypePersonNameMap);

        HttpResponseWrapper responseVictimWitnessDetails = caseCreateService.victimWitnessDetailsList(context.get("caseId"));
        caseCreateService.victimWitnessDetails(responseVictimWitnessDetails, context);



        switch(personType){
            case "witness","witnessChild","witnessExpert","witnessInterpreter","witnessIntimidated","witnessPolice",
                 "witnessPrisoner","witnessProfessional","witnessVulnerable":
                Map<String, List<String>> witnessIds = context.get("witnessMapIds");
                String witnessId = String.valueOf(witnessIds.get(personType));
                System.out.println("Created --" + personType + "-- Id --- is = " + witnessId );
                Map<String, List<String>> idWitnessNameMap = context.get("idWitnessNameMap");
                String witnessName = String.valueOf(idWitnessNameMap.get(witnessId));
                System.out.println("Person Id --" + witnessId + "-- FullName --- is = " + witnessName );
                String witnessNameVcaFormat = witnessName.substring(1, witnessName.indexOf(",")).toUpperCase()
                        + witnessName.substring(witnessName.indexOf(","), witnessName.length() - 1);
                System.out.println(witnessNameVcaFormat);
                personTypePersonNameMap.put(personType, witnessNameVcaFormat);
                context.set("personTypePersonNameMap", personTypePersonNameMap);
                break;
            case "victim","victimIntimidated","victimVulnerable","victimWitness","victimWitnessChild","victimWitnessExpert","victimWitnessInterpreter","victimWitnessIntimidated",
                 "victimWitnessPolice","victimWitnessPrisoner","victimWitnessProfessional","victimWitnessVulnerable":
                Map<String, List<String>> victimWitnessIds = context.get("victimWitnessMapIds");
                String victimWitnessId = String.valueOf(victimWitnessIds.get(personType));
                System.out.println("Created --" + personType + "-- Id --- is = " + victimWitnessId );
                Map<String, List<String>> idvictimWitnessNameMap = context.get("idVictimWitnessNameMap");
                String victimWitnessName = String.valueOf(idvictimWitnessNameMap.get(victimWitnessId));
                System.out.println("Person Id --" + victimWitnessId + "-- FullName --- is = " + victimWitnessName );
                String victimWitnessNameVcaFormat = victimWitnessName.substring(1, victimWitnessName.indexOf(",")).toUpperCase()
                        + victimWitnessName.substring(victimWitnessName.indexOf(","), victimWitnessName.length() - 1);
                System.out.println(victimWitnessNameVcaFormat);

                HttpResponseWrapper responseVictimWitnessName = caseCreateService.victimWitnessFirstnameSurname(context.get("caseId"),victimWitnessId);
                caseCreateService.victimWitnessFirstnameAndSurname(responseVictimWitnessName);



                personTypePersonNameMap.put(personType, victimWitnessNameVcaFormat);
                context.set("personTypePersonNameMap", personTypePersonNameMap);
                break;

            default : System.out.println("Person Type is not specified");
        }


    }

    @And("the {string} person details are verified")
    public void victimWitnessCategoryDetailsInCMS(String personType) {
        service.createCmsAuthToken(context);
        Map<String, String> idVictimDetailsMap = new HashMap<>();
        context.set("idVictimDetailsMap", idVictimDetailsMap);
//        Map<String, List<String>> victimWitnessId = context.get("victimWitnessId");

//        String caseUrn = context.get("caseUrn");
//        String caseId = context.get("caseId");
//        System.out.println(caseId);

        HttpResponseWrapper responseVictimWitnessIds = victimService.victimWitnessList(context.get("caseId"));
        victimService.victimWitnessIds(responseVictimWitnessIds, context);

        Map<String, List<String>> victimWitnessMapIds = context.get("victimMapIds");

//        String vicWitnessId = String.valueOf(victimWitnessId.get(personType));
//        System.out.println("Created --" +personType + "-- Id --- is = " + vicWitnessId );

//        for (String victimWitnessId : victimMapIds.get(personType)) {
//
//            HttpResponseWrapper resVictimWitnessDetails = caseCreateService.victimWitnessDetails(context.get("caseId"),victimWitnessId);
//
//            System.out.println(resVictimWitnessDetails);
//            caseCreateService.victimWitnessDetails(resVictimWitnessDetails,context,victimWitnessId);
//            victimService.victimWitnessIds(responseVictimWitnessIds, context);

//        }




//        switch (personType) {
//            case "victim":
//                Map<String, List<String>> victimMapIds = context.get("victimMapIds");
//                String victimIdDetails = String.valueOf(victimMapIds.get(personType));
//                System.out.println(victimIdDetails);
//
//            case "victimWitness":
//                Map<String, List<String>> victimWitnessMapIds = context.get("victimWitnessMapIds");
//                String victimWitnessIdDetails = String.valueOf(victimWitnessMapIds.get(personType));
//                System.out.println(victimWitnessIdDetails);
//
//            case "witness":
//                Map<String, List<String>> witnessMapIds = context.get("witnessMapIds");
//                String witnessIdDetails = String.valueOf(witnessMapIds.get(personType));
//                System.out.println(witnessIdDetails);
//        }
//
//        for (String id : victimWitnessMapIds.get(personType)){
//
//
//
//        }
//
//        String victimWitnessIdDetails = String.valueOf(victimMapIds.get(victimType));
//        System.out.println(victimWitnessIdDetails);


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
