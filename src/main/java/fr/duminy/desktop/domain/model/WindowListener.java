package fr.duminy.desktop.domain.model;

import javax.swing.*;
import java.util.EventListener;

public interface WindowListener extends EventListener {
    void windowRegistered(JInternalFrame window);

    void windowUnregistered(JInternalFrame window);
}
