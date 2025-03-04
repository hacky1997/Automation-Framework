package com.automation.interview.base;

import com.automation.interview.factory.DriverFactory;
import com.automation.interview.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class WebDriverManager {
    private static final Logger log = LoggerUtil.getLogger(WebDriverManager.class);
    private static WebDriver driver;
    private static final int IMPLICITLY_WAIT_FOR_TEN_SECONDS = 1000;
    private static final int PAGE_LOAD_TIMEOUT = 40;

    private WebDriverManager() {
        throw new IllegalStateException("WebDriverManager class");
    } // Private constructor to prevent instantiation

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_FOR_TEN_SECONDS));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                log.info("Closing WebDriver session.");
                driver.close();
                driver.quit();
                driver = null;
            } catch (RuntimeException e) {
                log.error("Closing WebDriver session.{}", e.getMessage());
            }
        }
    }
}
