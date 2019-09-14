package fr.duminy.desktop.core;

import com.google.common.annotations.VisibleForTesting;
import fr.duminy.desktop.Desktop;
import fr.duminy.desktop.WindowListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

@SuppressWarnings("serial") class DefaultDesktop extends JPanel implements Desktop {
    private final CustomDesktopManager manager;
    private final JDesktopPane desktopPane;

    DefaultDesktop() {
        super(new BorderLayout());

        manager = new CustomDesktopManager();
        desktopPane = new JDesktopPane();
        desktopPane.setDesktopManager(manager);
        desktopPane.addContainerListener(manager);

        add(desktopPane, CENTER);
    }

    @Override public final void addWindowListener(WindowListener windowListener) {
        manager.addWindowListener(windowListener);
    }

    @Override public final void removeWindowListener(WindowListener windowListener) {
        manager.removeWindowListener(windowListener);
    }

    @VisibleForTesting final JInternalFrame getSelectedFrame() {
        return desktopPane.getSelectedFrame();
    }

    final void addWindow(JInternalFrame window) {
        desktopPane.add(window);
    }

    final void removeWindow(JInternalFrame window) {
        desktopPane.remove(window);
    }
}
