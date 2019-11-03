package fr.duminy.desktop.adapter.swing;

import fr.duminy.desktop.application.service.UserInterface;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.slf4j.LoggerFactory.getLogger;

public class SwingUserInterface implements UserInterface {
    private static final Logger LOGGER = getLogger(SwingUserInterface.class);

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

    @Override
    public void start() {
        try {
            invokeAndWait(() -> frameReference.set(startUI()));
        } catch (InterruptedException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        JFrame frame = frameReference.getAndSet(null);
        if (frame != null) {
            frame.dispose();
        }
    }
}
