package com.automation.interview.factory;

import com.automation.interview.utils.ConfigReader;
import com.automation.interview.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.*;
import java.util.function.Supplier;

public class DriverFactory {
    private static final Logger log = LoggerUtil.getLogger(DriverFactory.class);
    private static final Map<String, Supplier<WebDriver>> drivers = new HashMap<>();
    static boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("browser.headless"));
    static boolean isIncognito = Boolean.parseBoolean(ConfigReader.getProperty("browser.incognito"));

    private DriverFactory() {
        throw new IllegalStateException("Utilities class");
    }

    // Register all browser drivers dynamically
    static {
    // drivers.put("chrome", () -> createDriverInstance("org.openqa.selenium.chrome.ChromeDriver"));
        drivers.put("chrome", DriverFactory::createChromeDriverInstance);
        drivers.put("firefox", DriverFactory::createFirefoxDriverInstance);
        drivers.put("edge", DriverFactory::createEdgeDriverInstance);
    }

    public static WebDriver getDriver() {
        String browser = ConfigReader.getProperty("browser.driver").toLowerCase().trim();
        log.info("Creating WebDriver instance for: {}", browser);
        return drivers.getOrDefault(browser, () -> {
            throw new IllegalArgumentException("Invalid browser: " + browser);
        }).get();
    }

    private static WebDriver createChromeDriverInstance() {
        try {
            ChromeOptions options = getChromeOptions();
            return new ChromeDriver(options);
        } catch (Exception e) {
            log.error("Failed to initialize ChromeDriver: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static WebDriver createFirefoxDriverInstance() {
        return new FirefoxDriver();
    }

    private static WebDriver createEdgeDriverInstance() {
        return new EdgeDriver();
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Set browser preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("safebrowsing.enabled", "true");

        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Add browser arguments
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-blink-features=AutomationControlled"); // Helps bypass bot detection

        if (isHeadless) options.addArguments("--headless=new"); // Uses new headless mode
        if (isIncognito) options.addArguments("--incognito");

        return options;
    }
}