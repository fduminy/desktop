package fr.duminy.desktop.core;

import fr.duminy.desktop.Desktop;
import fr.duminy.desktop.WindowListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

@SuppressWarnings("serial")
    /* default */ class DefaultDesktop extends JPanel implements Desktop {
    private final CustomDesktopManager manager;

    @SuppressWarnings("FieldCanBeLocal")
    private final JDesktopPane desktopPane;

    /* default */ DefaultDesktop() {
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
}
