package com.automation.interview.utils;

import com.automation.interview.base.WebDriverManager;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WebDriverHandler {
    private static final Logger log = LoggerUtil.getLogger(WebDriverHandler.class);
    private static WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor jsExecutor;
    private static int screenshotnumber = 0;

    public WebDriverHandler(WebDriver driver) {
        this.driver = WebDriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    // ✅ 1. Click an Element (With Explicit Wait)
    public void clickElement(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            log.info("Clicked on element: {}", locator);
        } catch (Exception e) {
            log.error("Unable to click on element: {}", locator, e);
            throw new RuntimeException(e);
        }
    }

    // ✅ 2. Send Keys to an Input Field (With Explicit Wait)
    public void sendKeys(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            log.info("Entered text '" + text + "' into: " + locator);
        } catch (Exception e) {
            log.error("Unable to enter text into: " + locator, e);
            throw new RuntimeException(e);
        }
    }

    // ✅ 3. Click Using JavaScript (For Elements That Are Not Clickable)
    public void jsClick(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            jsExecutor.executeScript("arguments[0].click();", element);
            log.info("Clicked using JavaScript on element: " + locator);
        } catch (Exception e) {
            log.error("JavaScript click failed on: " + locator, e);
            throw new RuntimeException(e);
        }
    }

    // ✅ 4. Scroll to Element Using JavaScript
    public void scrollToElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
            log.info("Scrolled to element: " + locator);
        } catch (Exception e) {
            log.error("Unable to scroll to element: " + locator, e);
        }
    }

    // ✅ 6. Handle JavaScript Alerts
    public void handleAlert(boolean accept) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            log.info("Alert found with text: {}", alertText);
            if (accept) {
                alert.accept();
                log.info("Alert accepted.");
            } else {
                alert.dismiss();
                log.info("Alert dismissed.");
            }
        } catch (Exception e) {
            log.error("No alert found!", e);
        }
    }

    // ✅ 7. Capture Screenshot
    public static void captureScreenshot(Scenario scenario) {
        screenshotnumber++;
        var screenshotName = "Attachment - " + screenshotnumber;
        byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshotBytes, "image/jpeg", screenshotName);
    }

    // ✅ 8. Switch to Window By Title
    public void switchToWindow(String windowTitle) {
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            for (String window : windowHandles) {
                driver.switchTo().window(window);
                if (driver.getTitle().equals(windowTitle)) {
                    log.info("Switched to window: " + windowTitle);
                    return;
                }
            }
            log.warn("Window with title {} not found.", windowTitle);
        } catch (Exception e) {
            log.error("Failed to switch window: {}", windowTitle, e);
        }
    }

    // ✅ 9. Switch to iFrame By Index
    public void switchToFrame(int index) {
        try {
            driver.switchTo().frame(index);
            log.info("Switched to frame index: " + index);
        } catch (Exception e) {
            log.error("Failed to switch to frame index: " + index, e);
        }
    }

    // ✅ 10. Switch to iFrame By Name or ID
    public void switchToFrame(String nameOrId) {
        try {
            driver.switchTo().frame(nameOrId);
            log.info("Switched to frame: " + nameOrId);
        } catch (Exception e) {
            log.error("Failed to switch to frame: " + nameOrId, e);
        }
    }

    // ✅ 11. Switch to Default Content
    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            log.info("Switched back to default content.");
        } catch (Exception e) {
            log.error("Failed to switch to default content.", e);
        }
    }

    public static void openWebPage() {
        String applicationUrl = ConfigReader.getProperty("baseUrl"); // Fetch URL from config
        driver.get(applicationUrl);

//        WebDriverUtils utils = new WebDriverUtils(WebDriverManager.getDriver());
//        utils.waitForPageLoad(10); // Waits until page is fully loaded

        log.info("Opened Web Page: {}", applicationUrl);
    }
}
