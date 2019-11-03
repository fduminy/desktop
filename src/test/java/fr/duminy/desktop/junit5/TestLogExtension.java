package fr.duminy.desktop.junit5;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

import static java.lang.String.format;
import static org.slf4j.MDC.put;
import static org.slf4j.MDC.remove;

public class TestLogExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final String TEST_NAME = "testName";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String testMethod = context.getTestMethod().map(Method::getName).orElse("");
        put(TEST_NAME, format("%s-%s", getClass().getSimpleName(), testMethod));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        remove(TEST_NAME);
    }
}
