package com.automation.interview.stepdefs;

import com.automation.interview.base.WebDriverManager;
import com.automation.interview.pageobjects.ApplicationLoginPage;
import com.automation.interview.utils.LoggerUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.time.Duration;

public class Hooks {
//    private static final WebDriver driver = WebDriverManager.getDriver();
    private static final Logger log = LoggerUtil.getLogger(Hooks.class);
    private static Scenario currentScenario;

    @Before
    public static void getScenarioName(Scenario scenario) {
        currentScenario = scenario;
        log.info("Starting Scenario: {}", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = WebDriverManager.getDriver(); // Get driver inside the method
        if (scenario.isFailed()) {
            log.error("Scenario Failed: {}", scenario.getName());
            captureScreenshot(driver, scenario);
        }

        // Logout only if login was performed
        if (wasLoginPerformed()) {
            log.info("Logging out user...");
            ApplicationLoginPage.logoutOfCurrentApp();
        }

        WebDriverManager.quitDriver();
    }

    private boolean wasLoginPerformed() {
        // Example logic: Check if login cookie or session exists
        WebDriver driver = WebDriverManager.getDriver();
        return driver.manage().getCookies().stream().anyMatch(cookie -> cookie.getName().equals("session_id"));
    }

    private void captureScreenshot(WebDriver driver, Scenario scenario) {
        try {
            turnOffImplicitWait(driver);
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/jpeg", "FailureScreenshot");
            log.info("Screenshot Attached: {}", "FailureScreenshot");
        } catch (Exception e) {
            log.error("Error while capturing screenshot", e);
        }
    }

    private void turnOffImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        log.info("Implicit wait turned OFF.");
    }
}
