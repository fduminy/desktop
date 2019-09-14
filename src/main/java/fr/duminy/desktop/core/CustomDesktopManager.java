package fr.duminy.desktop.core;

import fr.duminy.desktop.WindowListener;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.function.BiConsumer;

@SuppressWarnings("serial") class CustomDesktopManager extends DefaultDesktopManager implements ContainerListener {
    private final EventListenerList listeners = new EventListenerList();

    CustomDesktopManager() {
    }

    @Override public final void componentAdded(ContainerEvent e) {
        fireWindowRegistered(e.getChild());
    }

    @Override public final void componentRemoved(ContainerEvent e) {
        fireWindowUnregistered(e.getChild());
    }

    final void addWindowListener(WindowListener l) {
        listeners.add(WindowListener.class, l);
    }

    final void removeWindowListener(WindowListener l) {
        listeners.remove(WindowListener.class, l);
    }

    private void fireWindowRegistered(Component component) {
        notifyListeners(component, (window, listener) -> listener.windowRegistered(window));
    }

    private void fireWindowUnregistered(Component component) {
        notifyListeners(component, (window, listener) -> listener.windowUnregistered(window));
    }

    private void notifyListeners(Component component, BiConsumer<JInternalFrame, WindowListener> listenerConsumer) {
        if (component instanceof JInternalFrame) {
            Object[] listenerList = listeners.getListenerList();
            for (int i = listenerList.length - 2; i >= 0; i -= 2) {
                listenerConsumer.accept((JInternalFrame) component, (WindowListener) listenerList[i + 1]);
            }
        }
    }
}