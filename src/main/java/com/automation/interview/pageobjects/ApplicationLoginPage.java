package com.automation.interview.pageobjects;

import com.automation.interview.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApplicationLoginPage {
    private static final Logger log = LoggerUtil.getLogger(ApplicationLoginPage.class);
    private static WebDriver driver;

    @FindBy(id = "username")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    public ApplicationLoginPage() {
        PageFactory.initElements(driver, this);
    }

    public static void logoutOfCurrentApp() {
    }

    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginButton.click();
    }

    public void applicationLogin(String trim) {
    }
}
