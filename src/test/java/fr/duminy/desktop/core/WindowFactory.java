package fr.duminy.desktop.core;

import com.google.common.annotations.VisibleForTesting;

import javax.swing.*;
import java.beans.PropertyVetoException;

import static org.junit.jupiter.api.Assertions.fail;

/* default */ class WindowFactory {
    private int windowCount = 0;

    @VisibleForTesting JInternalFrame createWindow() {
        JInternalFrame frame = new Window(++windowCount);
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

    @SuppressWarnings("serial") private class Window extends JInternalFrame {
        /* default */ Window(int windowID) {
            super("window #" + windowID);
        }

        @Override public String toString() {
            return getTitle();
        }
    }
}
