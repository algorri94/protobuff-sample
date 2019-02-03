package org.algorrim.protobuff.sample;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.GuiceFilter;
import org.algorrim.protobuff.sample.dependencies.GuiceModulesInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Class that handles an HTTPServer using Jetty Connectors. Guice is used to inject the servlets that handle
 * the requests.
 */
public class HTTPServer {

    private static Logger log = LogManager.getLogger(HTTPServer.class);
    private Server server;

    /**
     * Instantiates a stateless server that listens to the port given.
     * @param config Configuration of the application
     */
    public HTTPServer(Configuration config, AbstractModule... modules) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.setContextPath("/");

        server = new Server();
        server.setHandler(contextHandler);

        contextHandler.addEventListener(new GuiceModulesInitializer(modules));
        contextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        contextHandler.addServlet(DefaultServlet.class, "/");

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(config.getPort());
        server.addConnector(serverConnector);
        log.info("HTTP Server created.");
    }

    /**
     * Starts the server
     */
    public void start() {
        try {
            server.start();
            log.info("HTTP Server started.");
        } catch (Exception e) {
            log.error("Couldn't start the HTTP Server.");
            log.error("Exception: ", e);
        }
    }

    /**
     * Stops the server
     */
    public void stop() {
        try {
            server.stop();
            log.info("HTTP Server stopped.");
        } catch (Exception e) {
            log.error("Couldn't stop the HTTP Server.");
            log.error("Exception: ", e);
        }
    }
}
