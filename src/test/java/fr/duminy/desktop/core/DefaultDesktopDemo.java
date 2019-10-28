package fr.duminy.desktop.core;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.edt.GuiQuery;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.slf4j.LoggerFactory.getLogger;

public class DefaultDesktopDemo {
    private static final Logger LOG = getLogger(DefaultDesktopDemo.class);
    private static final int OS_TASK_BAR_SIZE = 50;

    private final WindowFactory windowFactory = new WindowFactory();

    private DefaultDesktop desktop;

    public static void main(String[] args) {
        new DefaultDesktopDemo().init();
    }

    static JDesktopPane getJDesktopPane(DefaultDesktop desktop) {
        return desktop.getDesktopPane();
    }

    final void init() {
        desktop = execute(new GuiQuery<DefaultDesktop>() {
            @Override protected DefaultDesktop executeInEDT() {
                JPanel content = new JPanel(new BorderLayout());

                content.add(buildCommandPanel(), WEST);

                DefaultDesktop desktop = new DefaultDesktop();
                content.add(desktop, CENTER);

                JFrame frame = new JFrame("Demo");
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setContentPane(content);
                frame.setMinimumSize(new Dimension(800, 400));
                frame.pack();
                frame.setVisible(true);

                Dimension screenSize = getDefaultToolkit().getScreenSize();
                Dimension size = frame.getSize();
                frame.setLocation((int) (screenSize.getWidth() - size.getWidth()),
                                  (int) (screenSize.getHeight() - size.getHeight() - OS_TASK_BAR_SIZE));
                return desktop;
            }
        });
    }

    private JPanel buildCommandPanel() {
        JPanel windowCommands = new JPanel(new GridLayout(1, 1));
        createNewWindowButton(windowCommands);
        createCloseActiveWindowButton(windowCommands);

        ((GridLayout) windowCommands.getLayout()).setRows(windowCommands.getComponentCount());
        windowCommands
            .setBorder(createTitledBorder("<html><center>Window</center><br/><center>commands</center></html>"));

        return windowCommands;
    }

    @VisibleForTesting
    DefaultDesktop getDesktop() {
        return desktop;
    }

    private void createNewWindowButton(JPanel parent) {
        JButton button = new JButton("new window");
        button.addActionListener(e -> {
            JInternalFrame window = windowFactory.createWindow();
            addWindow(window);
        });
        parent.add(button);
    }

    private void createCloseActiveWindowButton(JPanel panel) {
        JButton result = new JButton("close active window");
        result.setName("closeActiveFrame");
        result.addActionListener(e -> {
            JInternalFrame frame = getSelectedFrame();
            if (frame == null) {
                LOG.warn("no active Window to close");
            } else {
                try {
                    frame.setClosed(true);
                } catch (PropertyVetoException e1) {
                    fail(e1.getMessage(), e1);
                }
                LOG.info("active Window is {}", frame.isClosed() ? "closed" : "not closed !!!");
            }
        });
        panel.add(result);
    }

    private JInternalFrame getSelectedFrame() {
        return getJDesktopPane(desktop).getSelectedFrame();
    }

    private void addWindow(JInternalFrame window) {
        getJDesktopPane(desktop).add(window);
    }
}
