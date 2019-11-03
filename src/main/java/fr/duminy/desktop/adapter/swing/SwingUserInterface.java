package fr.duminy.desktop.adapter.swing;

import fr.duminy.desktop.application.service.UserInterface;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SwingUserInterface implements UserInterface {
    private final AtomicReference<JFrame> frameReference = new AtomicReference<>();

    static JFrame startUI() {
        JFrame frame = new JFrame("Desktop");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new DefaultDesktop());
        frame.setMinimumSize(new Dimension(800, 400));
        frame.pack();
        frame.setVisible(true);

        Dimension screenSize = getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        frame.setLocation((int) (screenSize.getWidth() - size.getWidth()),
                (int) (screenSize.getHeight() - size.getHeight()));

        return frame;
    }

    @SuppressWarnings("unused")
    public void start() throws InvocationTargetException, InterruptedException {
        invokeAndWait(() -> frameReference.set(startUI()));
    }

    @SuppressWarnings("unused")
    public void stop() {
        JFrame frame = frameReference.getAndSet(null);
        if (frame != null) {
            frame.dispose();
        }
    }
}
