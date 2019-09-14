package fr.duminy.desktop.core;

import javax.swing.*;
import java.beans.PropertyVetoException;

import static org.junit.jupiter.api.Assertions.fail;

class WindowFactory {
    private int windowCount = 0;

    JInternalFrame createWindow() {
        @SuppressWarnings("serial") JInternalFrame jif = new JInternalFrame("window #" + ++windowCount) {
            @Override public String toString() {
                return getTitle();
            }
        };

        jif.setName(jif.getTitle());
        jif.setSize(200, 100);
        jif.setVisible(true);
        jif.setClosable(true);
        jif.setResizable(true);
        jif.setIconifiable(true);
        jif.setMaximizable(true);
        try {
            jif.setSelected(true);
        } catch (PropertyVetoException e) {
            fail(e);
        }

        return jif;
    }
}
