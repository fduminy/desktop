package fr.duminy.desktop.adapter.swing;

import fr.duminy.desktop.domain.model.Window;

import javax.swing.*;

class SwingWindow extends JInternalFrame implements Window {
    private static final long serialVersionUID = 1L;

    SwingWindow(String title) {
        super(title);
    }
}
