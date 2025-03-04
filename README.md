# 🚀 Selenium Automation Framework with JUnit 5 & Cucumber

## 📌 Overview
This is a **Selenium 4-based Automation Framework** using **JUnit 5, Cucumber, Log4J2, and Page Object Model (POM)**.  
The framework is designed for **cross-browser testing**, **parallel execution**, and **seamless reporting**.

## 📂 Project Folder Structure

```bash
src/main/java/com/automation
│─── base/                                           # WebDriver Management & Setup
│    ├── WebDriverManager.java                       # Singleton WebDriver instance
│
│─── factory/                                        # Browser Factory (Cross-Browser Support)
│    ├── DriverFactory.java                          # Manages WebDriver for different browsers
│
│─── stepdefs/                                       # Step Definitions for Cucumber
│    ├── Hooks.java                                  # Cucumber Hooks (Before/After Test Setup)
│    ├── ApplicationLoginSteps.java
│
│─── tests/                                          # JUnit Test Cases
│    ├── DriverTest.java                             # Example Test Case for JUnit
│
│─── utils/                                          # Utility Classes
│    ├── ConfigReader.java                           # Reads Configuration from Properties
│    ├── WebDriverHandler.java                       # Common WebDriver Actions
│    ├── LoggerUtil.java                             # Log4J2 Logger (Singleton)
│    ├── RetryExtension.java                         # Retry Mechanism for JUnit Failures
src/test/java/
│─── com/automation/runner/testRunner
│─── resources/                                      # Configuration & Test Data
│    ├── dataFiles/
│        │─── Browser/DriverConfig.properties        # Browser Configuration
│        │─── Database/DB.properties                 # Database Configuration
│        │─── LoginCreds/Credentials.properties      # Login Creds
│        │─── UI/ApplicationURL.properties           # List of Urls 
│    ├── features/E-commerce/flipkart.feature        # Cucumber Feature Files
│
│─── logs/
│    ├── log4j2.xml                                  # Logs for Test Execution
│    ├── application.log                             # Log4J2 logs
```
