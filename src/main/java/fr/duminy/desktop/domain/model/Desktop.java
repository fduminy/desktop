package fr.duminy.desktop.domain.model;

public interface Desktop {
    void addWindowListener(WindowListener windowListener);

    void removeWindowListener(WindowListener windowListener);

    void addShortcut(Shortcut shortcut);
}
