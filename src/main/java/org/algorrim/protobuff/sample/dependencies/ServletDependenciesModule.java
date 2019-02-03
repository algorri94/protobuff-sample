package org.algorrim.protobuff.sample.dependencies;

import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.Set;

/**
 * Class that handles the Injection of the Servlets to the web server.
 */
public class ServletDependenciesModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bindServlets();
        serve("/*").with(GuiceContainer.class);
    }

    private void bindServlets() {
        for (Class<?> resource : findServlets()) {
            bind(resource);
        }
    }

    private Set<Class<?>> findServlets() {
        return new PackagesResourceConfig("org.algorrim.protobuff.sample").getClasses();
    }
}
