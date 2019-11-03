package fr.duminy.desktop.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import fr.duminy.desktop.application.Boot;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.conditions.ArchConditions.resideOutsideOfPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "fr.duminy.desktop")
class ArchitectureTest {
    @ArchTest
    private static final ArchRule ARCHITECTURE = onionArchitecture()
            .domainModels("fr.duminy.desktop.domain.model..")
            .domainServices("fr.duminy.desktop.domain.service..")
            .applicationServices("fr.duminy.desktop.application.service..")
            .adapter("swingUI", "fr.duminy.desktop.adapter.swing..");

    @ArchTest
    private static final ArchRule PACKAGES = noClasses().that(not(resideInAnyPackage("fr.duminy.desktop.application", "fr.duminy.desktop.architecture")))
            .should(resideOutsideOfPackages(
                    "fr.duminy.desktop.domain.model..",
                    "fr.duminy.desktop.domain.service..",
                    "fr.duminy.desktop.application.service..",
                    "fr.duminy.desktop.adapter.swing.."
            ));

    @ArchTest
    private static final ArchRule PICO_CONTAINER = noClasses().that(not(equivalentTo(Boot.class)))
            .should().dependOnClassesThat().resideInAnyPackage("org.picocontainer..");

    @ArchTest
    private static final ArchRule SWING_AND_AWT = noClasses().that().resideOutsideOfPackage("fr.duminy.desktop.adapter.swing..")
            .should().dependOnClassesThat().resideInAnyPackage("java.awt..", "javax.swing..");
}
