package com.cps.fct.e2e.stepdefs.service;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ReportTestStepDefs {

    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        // Cucumber injects Scenario here
        this.scenario = scenario;
    }

    @Given("I print a message {string}")
    public void iPrintAMessage(String message) {
        // Console output
        System.out.println("REPORT TEST PRINT: " + message);

    }

    @Then("the test should pass")
    public void theTestShouldPass() {
        // Console output
        System.out.println("REPORT TEST PRINT: ");
    }

    @Then("the test should fail")
    public void theTestShouldFail() {
        // Console output
        System.out.println("REPORT TEST PRINT: ");
    }
}