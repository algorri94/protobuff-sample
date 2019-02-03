package org.algorrim.protobuff.sample.dependencies;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.algorrim.protobuff.sample.Configuration;
import org.algorrim.protobuff.sample.persistence.IPersistence;
import org.algorrim.protobuff.sample.persistence.IController;
import org.algorrim.protobuff.sample.persistence.file.RollingFileProtobuffConsumer;
import org.algorrim.protobuff.sample.persistence.file.RollingFileProtobuffController;
import org.algorrim.protobuff.sample.persistence.file.RollingFileProtobuffPersistence;

/**
 * Dependency Injection class. It takes care of binding instances to dependencies declared to be injected.
 */
public class DependenciesModule extends AbstractModule {

    private Configuration configuration;

    public DependenciesModule(Configuration config) {
        this.configuration = config;
    }

    @Override
    protected void configure() {
        bind(IController.class).to(RollingFileProtobuffController.class);
        bind(Runnable.class).annotatedWith(Names.named("consumer")).to(RollingFileProtobuffConsumer.class);
        bind(int.class).annotatedWith(Names.named("rolling-timeout-ms")).toInstance(configuration.getRolling_timeout_ms());
        bind(String.class).annotatedWith(Names.named("file-folder")).toInstance(configuration.getFolder());
        bind(IPersistence.class).to(RollingFileProtobuffPersistence.class);
    }

}