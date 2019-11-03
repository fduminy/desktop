package fr.duminy.desktop.application;

import fr.duminy.desktop.junit5.TestLogExtension;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.swing.core.ComponentFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.swing.*;

import static org.assertj.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;

@ExtendWith({SoftAssertionsExtension.class, TestLogExtension.class})
class BootTest {
    private final SoftAssertions softly = new SoftAssertions();

    @Test
    void main() {
        ComponentFinder finder = finderWithNewAwtHierarchy();

        Boot.main(new String[0]);

        JFrame frame = finder.findByType(JFrame.class);
        softly.assertThat(frame).extracting(JFrame::getTitle).isEqualTo("Desktop");
        softly.assertThat(frame).extracting(JFrame::isVisible).isEqualTo(true);
    }
}