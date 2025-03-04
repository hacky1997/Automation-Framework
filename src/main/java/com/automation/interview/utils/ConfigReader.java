package com.automation.interview.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static final String[] CONFIG_FILES = {
            "src/test/resources/dataFiles/Browser/DriverConfig.properties",
            "src/test/resources/dataFiles/Database/DB.properties",
            "src/test/resources/dataFiles/LoginCreds/Credentials.properties",
            "src/test/resources/dataFiles/UI/ApplicationURL.properties"
    };

    private ConfigReader() {
        throw new IllegalStateException("Config Reader Class");
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        for (String filePath : CONFIG_FILES) {
            try (FileInputStream fis = new FileInputStream(filePath)) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("Error loading properties file: " + filePath + " | " + e.getMessage());
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key, "Key not found: " + key);
    }
}
