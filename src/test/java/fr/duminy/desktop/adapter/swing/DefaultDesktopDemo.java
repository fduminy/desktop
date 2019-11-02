package fr.duminy.desktop.adapter.swing;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.edt.GuiQuery;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

import static fr.duminy.desktop.adapter.swing.SwingUserInterface.startUI;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;
import static javax.swing.BorderFactory.createTitledBorder;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.slf4j.LoggerFactory.getLogger;

public class DefaultDesktopDemo {
    private static final Logger LOG = getLogger(DefaultDesktopDemo.class);

    private final WindowFactory windowFactory = new WindowFactory();

    private DefaultDesktop desktop;

    public static void main(String[] args) {
        new DefaultDesktopDemo().init();
    }

    static JDesktopPane getJDesktopPane(DefaultDesktop desktop) {
        return desktop.getDesktopPane();
    }

    final void init() {
        desktop = execute(new CreateDesktopQuery());
    }

    @VisibleForTesting
    DefaultDesktop getDesktop() {
        return desktop;
    }

    private class CreateDesktopQuery extends GuiQuery<DefaultDesktop> {
        @Override
        protected DefaultDesktop executeInEDT() {
            JFrame frame = startUI();
            DefaultDesktop desktop = (DefaultDesktop) frame.getContentPane();
            JPanel content = new JPanel(new BorderLayout());
            content.add(buildCommandPanel(), WEST);
            content.add(desktop, CENTER);
            frame.setContentPane(content);
            return desktop;
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

        private void createNewWindowButton(JPanel parent) {
            JButton button = new JButton("new window");
            button.addActionListener(e -> {
                JInternalFrame window = windowFactory.createWindow();
                getJDesktopPane(desktop).add(window);
            });
            parent.add(button);
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private void createCloseActiveWindowButton(JPanel panel) {
            JButton result = new JButton("close active window");
            result.setName("closeActiveFrame");
            result.addActionListener(e -> {
                JInternalFrame frame = getJDesktopPane(desktop).getSelectedFrame();
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
    }
}
