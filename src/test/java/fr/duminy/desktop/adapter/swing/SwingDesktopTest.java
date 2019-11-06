package fr.duminy.desktop.adapter.swing;

import com.google.common.annotations.VisibleForTesting;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit5.JGivenExtension;
import fr.duminy.desktop.junit5.TestLogExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({JGivenExtension.class, TestLogExtension.class})
@SuppressWarnings({"serial", "squid:S2699"})
class SwingDesktopTest extends AssertJSwingTestCaseTemplateJUnit5 {
    @SuppressWarnings("unused")
    @ScenarioStage
    private DesktopStage desktopStage;
    @SuppressWarnings("unused")
    @ScenarioStage
    private ShortcutStage shortcutStage;

    @VisibleForTesting
    @Test
    void windowRegistered_event_is_sent_to_listeners() {
        desktopStage.given().desktop_is_empty();
        desktopStage.when().addWindowListener().and().create_new_window();
        desktopStage.then().windowRegistered_is_called().and().no_more_listener_method_is_called();
    }

    @VisibleForTesting
    @Test
    void windowRegistered_event_is_not_sent_to_listeners() {
        desktopStage.given().desktop_is_empty().and().addWindowListener();
        desktopStage.when().removeWindowListener().and().create_new_window();
        desktopStage.then().windowRegistered_is_not_called().and().no_more_listener_method_is_called();
    }

    @VisibleForTesting
    @Test
    void windowUnregistered_event_is_sent_to_listeners() {
        desktopStage.given().desktop_is_empty().and().addWindowListener().and().create_new_window();
        desktopStage.when().close_window();
        desktopStage.then().windowRegistered_is_called().and().windowUnregistered_is_called().and()
             .no_more_listener_method_is_called();
    }

    @VisibleForTesting
    @Test
    void windowUnregistered_event_is_not_sent_to_listeners() {
        desktopStage.given().desktop_is_empty().and().addWindowListener().and().create_new_window().and()
             .removeWindowListener();
        desktopStage.when().close_window();
        desktopStage.then().windowRegistered_is_called().and().windowUnregistered_is_not_called().and()
             .no_more_listener_method_is_called();
    }

    @VisibleForTesting
    @Test
    void shortcut_is_added() {
        desktopStage.when().desktop_is_empty();
        shortcutStage.when().add_shortcut();
        shortcutStage.then().shortcut_is_added();
    }
}
