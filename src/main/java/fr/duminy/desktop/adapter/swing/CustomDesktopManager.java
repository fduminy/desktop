package fr.duminy.desktop.adapter.swing;

import com.google.common.annotations.VisibleForTesting;
import fr.duminy.desktop.domain.model.Window;
import fr.duminy.desktop.domain.model.WindowListener;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.function.BiConsumer;

@SuppressWarnings("serial")
class CustomDesktopManager extends DefaultDesktopManager implements ContainerListener {
    private final EventListenerList listeners = new EventListenerList();

    @Override public final void componentAdded(ContainerEvent event) {
        fireWindowRegistered(event.getChild());
    }

    @Override public final void componentRemoved(ContainerEvent event) {
        fireWindowUnregistered(event.getChild());
    }

    @VisibleForTesting
    final void addWindowListener(WindowListener listener) {
        listeners.add(WindowListener.class, listener);
    }

    @VisibleForTesting
    final void removeWindowListener(WindowListener listener) {
        listeners.remove(WindowListener.class, listener);
    }

    private void fireWindowRegistered(Component component) {
        notifyListeners(component, (window, listener) -> listener.windowRegistered(window));
    }

    private void fireWindowUnregistered(Component component) {
        notifyListeners(component, (window, listener) -> listener.windowUnregistered(window));
    }

    private void notifyListeners(Component component, BiConsumer<Window, WindowListener> listenerConsumer) {
        if (component instanceof SwingWindow) {
            Object[] listenerList = listeners.getListenerList();
            for (int i = listenerList.length - 2; i >= 0; i -= 2) {
                listenerConsumer.accept((SwingWindow) component, (WindowListener) listenerList[i + 1]);
            }
        }
    }
}