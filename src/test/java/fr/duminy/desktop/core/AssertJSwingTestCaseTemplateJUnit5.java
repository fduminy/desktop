package fr.duminy.desktop.core;

import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.swing.edt.FailOnThreadViolationRepaintManager.install;
import static org.assertj.swing.edt.FailOnThreadViolationRepaintManager.uninstall;

public abstract class AssertJSwingTestCaseTemplateJUnit5 extends AssertJSwingTestCaseTemplate {
    @BeforeAll public static void setUpOnce() {
        install();
    }

    @AfterAll public static void tearDownOnce() {
        uninstall();
    }

    @BeforeEach public final void setUp() {
        this.setUpRobot();
        this.onSetUp();
    }

    @AfterEach public final void tearDown() {
        try {
            this.onTearDown();
        } finally {
            this.cleanUp();
        }
    }

    abstract void onSetUp();

    abstract void onTearDown();
}
