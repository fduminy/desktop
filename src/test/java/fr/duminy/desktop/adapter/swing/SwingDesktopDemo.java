package fr.duminy.desktop.adapter.swing;

import fr.duminy.desktop.domain.model.Shortcut;
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

public class SwingDesktopDemo {
    private static final Logger LOG = getLogger(SwingDesktopDemo.class);

    private final WindowFactory windowFactory = new WindowFactory();

    private SwingDesktop desktop;

    public static void main(String[] args) {
        new SwingDesktopDemo().init();
    }

    @VisibleForTesting
    static JDesktopPane getJDesktopPane(SwingDesktop desktop) {
        return desktop.getDesktopPane();
    }

    @VisibleForTesting
    final void init() {
        desktop = execute(new CreateDesktopQuery());
    }

    @VisibleForTesting
    SwingDesktop getDesktop() {
        return desktop;
    }

    private class CreateDesktopQuery extends GuiQuery<SwingDesktop> {
        @Override
        protected SwingDesktop executeInEDT() {
            JFrame frame = startUI();
            SwingDesktop desktop = (SwingDesktop) frame.getContentPane();
            JPanel content = new JPanel(new BorderLayout());
            content.add(buildCommandPanel(), WEST);
            content.add(desktop, CENTER);
            frame.setContentPane(content);
            return desktop;
        }

        private JPanel buildCommandPanel() {
            GridLayout layout = new GridLayout(1, 1);
            JPanel commands = new JPanel(layout);
            createNewWindowButton(commands);
            createCloseActiveWindowButton(commands);
            createNewShortcutButton(commands);

            layout.setRows(commands.getComponentCount());
            commands
                    .setBorder(createTitledBorder("<html><center>Window</center><br/><center>commands</center></html>"));

            return commands;
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

        private void createNewShortcutButton(JPanel parent) {
            JButton button = new JButton("new shortcut");
            button.addActionListener(e -> desktop.addShortcut(new Shortcut("icon")));
            parent.add(button);
        }
    }
}
