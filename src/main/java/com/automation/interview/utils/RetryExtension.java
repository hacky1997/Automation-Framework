package com.automation.interview.utils;

import org.junit.jupiter.api.extension.*;
import org.opentest4j.TestAbortedException;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryExtension implements TestExecutionExceptionHandler, BeforeTestExecutionCallback {
    private static final int MAX_RETRIES = Integer.parseInt(ConfigReader.getProperty("retry.count"));
    private static final ThreadLocal<AtomicInteger> retryCount = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        int attempts = retryCount.get().incrementAndGet();
        if (attempts <= MAX_RETRIES) {
            throw new TestAbortedException("Retrying test...");
        } else {
            throw throwable;
        }
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        retryCount.set(new AtomicInteger(0));
    }
}
