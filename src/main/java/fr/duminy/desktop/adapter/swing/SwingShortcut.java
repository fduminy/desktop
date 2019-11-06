package fr.duminy.desktop.adapter.swing;

import fr.duminy.desktop.domain.model.Shortcut;

import javax.swing.*;

class SwingShortcut extends JLabel {
    private static final long serialVersionUID = 1L;

    private final Shortcut shortcut;

    SwingShortcut(Shortcut shortcut) {
        this.shortcut = shortcut;
    }

    Shortcut getShortcut() {
        return shortcut;
    }
}
