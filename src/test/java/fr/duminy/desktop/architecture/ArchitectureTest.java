package fr.duminy.desktop.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import fr.duminy.desktop.application.Boot;
import fr.duminy.desktop.junit5.TestLogExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.tngtech.archunit.base.DescribedPredicate.doNot;
import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.core.domain.JavaModifier.ABSTRACT;
import static com.tngtech.archunit.lang.conditions.ArchConditions.resideOutsideOfPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static java.util.Arrays.stream;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "fr.duminy.desktop")
@ExtendWith(TestLogExtension.class)
class ArchitectureTest {
    @ArchTest
    private static final ArchRule ARCHITECTURE = onionArchitecture()
            .domainModels("fr.duminy.desktop.domain.model..")
            .domainServices("fr.duminy.desktop.domain.service..")
            .applicationServices("fr.duminy.desktop.application.service..")
            .adapter("swingUI", "fr.duminy.desktop.adapter.swing..");

    @ArchTest
    private static final ArchRule PACKAGES = noClasses().that(not(resideInAnyPackage("fr.duminy.desktop.application", "fr.duminy.desktop.architecture", "fr.duminy.desktop.junit5")))
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

    @ArchTest
    private static final ArchRule TEST_LOGS = classes().that().haveSimpleNameEndingWith("Test").and(doNot(haveAbstractClassModifier()))
            .should().beAnnotatedWith(new DescribedPredicate<JavaAnnotation>("TestLogExtension") {
                @Override
                public boolean apply(JavaAnnotation input) {
                    Optional<Object> value = input.get("value");
                    return input.getRawType().isAssignableTo(ExtendWith.class) &&
                            stream(value.transform(val -> (JavaClass[]) val).or(new JavaClass[0]))
                                    .map(JavaClass::getFullName)
                                    .anyMatch(name -> TestLogExtension.class.getName().equals(name));
                }
            });

    private static DescribedPredicate<JavaClass> haveAbstractClassModifier() {
        return new DescribedPredicate<JavaClass>("have modifier %s", ABSTRACT) {

            @Override
            public boolean apply(JavaClass input) {
                return input.getModifiers().contains(ABSTRACT);
            }
        };
    }
}
