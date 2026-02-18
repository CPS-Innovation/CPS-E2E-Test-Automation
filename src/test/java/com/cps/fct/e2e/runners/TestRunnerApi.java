package com.cps.fct.e2e.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"com.cps.fct.e2e.hooks", "com.cps.fct.e2e.stepdefs"},
        tags = "@ui",
        plugin = {"pretty",
                "html:target/cucumber-reports.html"}
)
public class TestRunnerApi {
}
