package fr.duminy.desktop.domain.model;

import java.util.EventListener;

public interface WindowListener extends EventListener {
    void windowRegistered(Window window);

    void windowUnregistered(Window window);
}
