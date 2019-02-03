package org.algorrim.protobuff.sample.dependencies;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.algorrim.protobuff.sample.Configuration;

/**
 * Guice Context Initializer. It creates the dependency Injector so that the framework can
 * instantiate all the dependencies.
 */
public class GuiceModulesInitializer extends GuiceServletContextListener {

    private Configuration configuration;
    private AbstractModule[] modules;

    public GuiceModulesInitializer(AbstractModule... modules) {
        this.modules = modules;
    }

    protected Injector getInjector() {
        return Guice.createInjector(modules);
    }
}
