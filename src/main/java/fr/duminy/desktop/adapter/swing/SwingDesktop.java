package fr.duminy.desktop.adapter.swing;

import com.google.common.annotations.VisibleForTesting;
import fr.duminy.desktop.domain.model.Desktop;
import fr.duminy.desktop.domain.model.Shortcut;
import fr.duminy.desktop.domain.model.WindowListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;

@SuppressWarnings("serial")
class SwingDesktop extends JPanel implements Desktop {
    private final SwingDesktopManager manager;
    private final JDesktopPane desktopPane;

    SwingDesktop() {
        super(new BorderLayout());

        manager = new SwingDesktopManager();
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

    @Override
    public final void addShortcut(Shortcut shortcut) {
        SwingShortcut swingShortcut = new SwingShortcut(shortcut);
        swingShortcut.setText(shortcut.getLabel());
        swingShortcut.setSize(swingShortcut.getPreferredSize());
        desktopPane.add(swingShortcut);
        desktopPane.validate();
    }

    @VisibleForTesting
    JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}
