package fr.duminy.desktop.adapter.swing;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import fr.duminy.desktop.domain.model.Shortcut;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.TypeMatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.BasicComponentFinder.finderWithCurrentAwtHierarchy;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings({"unused", "UnusedReturnValue"})
class ShortcutStage extends Stage<ShortcutStage> {
    @ScenarioState
    private final ComponentFinder finder;
    @ScenarioState
    private SwingDesktop desktop;
    private Shortcut shortcut;

    ShortcutStage() {
        super();
        finder = finderWithCurrentAwtHierarchy();
    }

    ShortcutStage add_shortcut() {
        shortcut = execute(() -> {
            Shortcut shortcut = new Shortcut("shortcut #" + desktop.getComponents().length + 1);
            desktop.addShortcut(shortcut);
            return shortcut;
        });
        return self();
    }

    ShortcutStage shortcut_is_added() {
        assertThat(shortcut).isNotNull();
        SwingShortcut swingShortcut = (SwingShortcut) finder.find(new TypeMatcher(SwingShortcut.class));
        assertThat(swingShortcut).isNotNull();
        assertAll("shortcut",
                () -> assertThat(swingShortcut.getShortcut()).isSameAs(shortcut),
                () -> assertThat(swingShortcut.getText()).isSameAs(shortcut.getLabel())
        );
        return self();
    }
}
