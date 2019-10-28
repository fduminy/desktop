package fr.duminy.desktop.core;

import com.tngtech.jgiven.Stage;
import fr.duminy.desktop.WindowListener;

import javax.swing.*;

import static fr.duminy.desktop.core.DefaultDesktopDemo.getJDesktopPane;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.*;

@SuppressWarnings("unused")
class DesktopStage extends Stage<DesktopStage> {
    private final WindowFactory windowFactory = new WindowFactory();
    private final DefaultDesktop desktop;
    private WindowListener listener;
    private JInternalFrame window;

    DesktopStage() {
        super();
        DefaultDesktopDemo demo = new DefaultDesktopDemo();
        demo.init();

        desktop = demo.getDesktop();
    }

    DesktopStage no_windows_is_opened() {
        return self();
    }

    DesktopStage addWindowListener() {
        listener = mock(WindowListener.class);
        desktop.addWindowListener(listener);
        return self();
    }

    DesktopStage removeWindowListener() {
        desktop.removeWindowListener(listener);
        return self();
    }

    DesktopStage create_new_window() {
        window = execute(() -> {
            JInternalFrame window = windowFactory.createWindow();
            getJDesktopPane(desktop).add(window);
            return window;
        });
        return self();
    }

    DesktopStage close_window() {
        execute(() -> {
            getJDesktopPane(desktop).remove(window);
            return null;
        });
        return self();
    }

    DesktopStage windowRegistered_is_called() {
        verify(listener).windowRegistered(window);
        return self();
    }

    DesktopStage windowRegistered_is_not_called() {
        verify(listener, never()).windowRegistered(window);
        return self();
    }

    DesktopStage windowUnregistered_is_called() {
        verify(listener).windowUnregistered(window);
        return self();
    }

    DesktopStage windowUnregistered_is_not_called() {
        verify(listener, never()).windowUnregistered(window);
        return self();
    }

    DesktopStage no_more_listener_method_is_called() {
        verifyNoMoreInteractions(listener);
        return self();
    }
}
