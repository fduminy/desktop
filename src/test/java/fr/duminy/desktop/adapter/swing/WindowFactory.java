package fr.duminy.desktop.adapter.swing;

import org.assertj.core.util.VisibleForTesting;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.fail;

class WindowFactory {
    private static final AtomicInteger WINDOW_COUNT = new AtomicInteger(1);

    @VisibleForTesting
    JInternalFrame createWindow() {
        JInternalFrame frame = new Window(WINDOW_COUNT.getAndIncrement());
        frame.setName(frame.getTitle());
        frame.setSize(200, 100);
        frame.setVisible(true);
        frame.setClosable(true);
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.setMaximizable(true);
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException e) {
            fail(e);
        }

        return frame;
    }

    @SuppressWarnings("serial")
    private static class Window extends JInternalFrame {
        Window(int windowID) {
            super("window #" + windowID);
        }

        @Override public String toString() {
            return getTitle();
        }
    }
}
