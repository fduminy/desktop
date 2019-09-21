package fr.duminy.desktop.core;

import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit5.JGivenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(JGivenExtension.class) @SuppressWarnings({ "serial", "squid:S2699" })
    /* default */ class DefaultDesktopTest extends AbstractDesktopJGivenTest {
    @SuppressWarnings("unused") @ScenarioStage private DesktopStage stage;

    /* default */
    @Test void windowRegistered_event_is_sent_to_listeners() {
        stage.given().no_windows_is_opened();
        stage.when().addWindowListener().and().create_new_window();
        stage.then().windowRegistered_is_called().and().no_more_listener_method_is_called();
    }

    /* default */
    @Test void windowRegistered_event_is_not_sent_to_listeners() {
        stage.given().no_windows_is_opened().and().addWindowListener();
        stage.when().removeWindowListener().and().create_new_window();
        stage.then().windowRegistered_is_not_called().and().no_more_listener_method_is_called();
    }

    /* default */
    @Test void windowUnregistered_event_is_sent_to_listeners() {
        stage.given().no_windows_is_opened().and().addWindowListener().and().create_new_window();
        stage.when().close_window();
        stage.then().windowRegistered_is_called().and().windowUnregistered_is_called().and()
             .no_more_listener_method_is_called();
    }

    /* default */
    @Test void windowUnregistered_event_is_not_sent_to_listeners() {
        stage.given().no_windows_is_opened().and().addWindowListener().and().create_new_window().and()
             .removeWindowListener();
        stage.when().close_window();
        stage.then().windowRegistered_is_called().and().windowUnregistered_is_not_called().and()
             .no_more_listener_method_is_called();
    }
}
