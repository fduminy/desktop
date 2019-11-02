package fr.duminy.desktop.application;

import com.google.common.annotations.VisibleForTesting;
import fr.duminy.desktop.core.DefaultDesktop;

import javax.swing.*;
import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public final class Boot {
    private Boot() {
        // utility class
    }

    public static void main(String[] args) {
        startUI();
    }

    @VisibleForTesting
    public static JFrame startUI() {
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
}
