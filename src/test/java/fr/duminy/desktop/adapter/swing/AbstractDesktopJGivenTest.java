package fr.duminy.desktop.adapter.swing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Method;

import static java.lang.String.format;
import static org.slf4j.MDC.put;
import static org.slf4j.MDC.remove;

@SuppressWarnings("unused")
abstract class AbstractDesktopJGivenTest extends AssertJSwingTestCaseTemplateJUnit5 {
    private static final String TEST_NAME = "testName";

    @Override
    void onSetUp() {
        // nothing to do
    }

    @Override
    void onTearDown() {
        // nothing to do
    }

    @BeforeEach private void setupMDC(TestInfo testInfo) {
        String testMethod = testInfo.getTestMethod().map(Method::getName).orElse("");
        put(TEST_NAME, format("%s-%s", getClass().getSimpleName(), testMethod));
    }

    @AfterEach private void removeMDC() {
        remove(TEST_NAME);
    }
}
