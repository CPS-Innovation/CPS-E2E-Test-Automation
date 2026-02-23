package com.cps.fct.e2e.report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CucumberReportGeneratorTest {

    @Test
    void generateCucumberReport() {
        String jsonPath = "target/cucumber-report/cucumber.json";

        // Where you want the dashboard HTML to go
        File reportOutputDir = new File("target/cucumber-report");

        List<String> jsonFiles = Collections.singletonList(jsonPath);

        Configuration config = new Configuration(
                reportOutputDir,
                "CPS E2E Test Automation"
        );

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();
    }
}
