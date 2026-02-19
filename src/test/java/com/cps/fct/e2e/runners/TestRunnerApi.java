package com.cps.fct.e2e.runners;

//import static io.cucumber.core.options.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.*;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/*
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"com.cps.fct.e2e.hooks", "com.cps.fct.e2e.stepdefs"},
        tags = "@ui",
        plugin = {"pretty",
                "html:target/cucumber-reports.html"}
)*/

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.cps.fct.e2e.stepdefs")
@SelectClasspathResource("src/test/resources/Features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.cps.fct.e2e.stepdefs")
//@ConfigurationParameter(key = FEATURES_PROPERTY_NAME,value = "src/test/resources/testcases/searchGoogle.feature")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME,value = "@test")
@ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,value = "pretty, html:target/cucumber-report/cucumber.html")
public class TestRunnerApi {
}



