package fr.duminy.desktop.domain.model;

import java.io.Serializable;

public class Shortcut implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String label;

    public Shortcut(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
