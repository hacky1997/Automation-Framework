package com.automation.interview.stepdefs;

import com.automation.interview.pageobjects.ApplicationLoginPage;
import io.cucumber.java.en.Given;

public class ApplicationLoginSteps {
    ApplicationLoginPage applicationLoginPage = new ApplicationLoginPage();

    @Given("User successfully logs into the application as an {string}")
    public void userSuccessfullyLogsIntoTheApplicationAsAn(String userID) {
        applicationLoginPage.applicationLogin(userID.trim());
    }
}
