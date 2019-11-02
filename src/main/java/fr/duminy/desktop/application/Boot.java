package fr.duminy.desktop.application;

import fr.duminy.desktop.adapter.swing.SwingUserInterface;
import fr.duminy.desktop.application.service.UserInterface;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.lifecycle.ReflectionLifecycleStrategy;

import static java.lang.Runtime.getRuntime;

public final class Boot {
    private Boot() {
        // utility class
    }

    public static void main(String[] args) {
        PicoBuilder builder = new PicoBuilder();
        builder.withCaching().withLifecycle(ReflectionLifecycleStrategy.class);

        MutablePicoContainer container = builder.build();
        container.addComponent(UserInterface.class, SwingUserInterface.class);
        container.start();

        getRuntime().addShutdownHook(new Thread(container::stop));
    }
}
