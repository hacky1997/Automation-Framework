package com.automation.interview.runner.testRunner;

import com.automation.interview.base.WebDriverManager;
import com.automation.interview.utils.ConfigReader;
import com.automation.interview.utils.LoggerUtil;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Cucumber.class)
@CucumberOptions(tags = "@Test and @Demo",
        plugin = {"pretty", "html:target/cucumber-html-report/index.html", "json:target/cucumber-reports/cucumber.json"},
        monochrome = true,
        stepNotifications = true,
        //strict = true,
        glue = "com.automation.interview.stepdefs",
        publish = true,
        features = "src/test/resources/features/E-commerce"
)

public class DriverTest {
    private static final Logger log = LoggerUtil.getLogger(DriverTest.class);

    @BeforeClass
    public static void setup() {
        WebDriver driver = WebDriverManager.getDriver();
        String baseUrl = ConfigReader.getProperty("Application.URL");
        log.info("Launching the URL: {}", baseUrl);
        driver.get(baseUrl);
    }

    @AfterClass
    public static void quit() {
        WebDriverManager.quitDriver();
    }
}
