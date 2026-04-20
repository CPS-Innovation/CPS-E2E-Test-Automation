package com.cps.fct.e2e.runners;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.OBJECT_FACTORY_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.cps.fct.e2e")
//@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@S1_S2_15300_15789 or @S3_S4_15791_15793" )
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@16007_cms_case_contacts_for_victim_witness" )
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber-report/cucumber.json"
)
@ConfigurationParameter(
        key = OBJECT_FACTORY_PROPERTY_NAME,
        value = "com.cps.fct.e2e.support.CucumberObjectFactory"
)
public class TestRunnerApi {
//    static {
//        System.setProperty("cucumber.filter.tags",
//                        "@S1_S2_15300_15789" +
//                        "@S3_S4_15791_15793"
//        );
//    }
    @Test
    void generateCucumberReport() {
        String jsonPath = "target/cucumber-report/cucumber.json";
        File reportOutputDir = new File("target/cucumber-report");
        List<String> jsonFiles = Collections.singletonList(jsonPath);
        Configuration config = new Configuration(reportOutputDir,"CPS E2E Test Automation");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();
    }
}