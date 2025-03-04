package com.automation.interview.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private LoggerUtil() {throw new IllegalStateException("Logger Util Class");} // Prevent instantiation

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
