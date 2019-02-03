package org.algorrim.protobuff.sample;

import org.algorrim.protobuff.sample.dependencies.DependenciesModule;
import org.algorrim.protobuff.sample.dependencies.ServletDependenciesModule;
import org.apache.commons.cli.*;

public class ProtobuffSampleApplication {

    public static void main(String[] args) {
        Options options = new Options();

        Option port = new Option("p", "port", true, "Port the server will listen to. Default 8090.");
        port.setRequired(false);
        port.setType(int.class);
        options.addOption(port);

        Option folder = new Option("f", "folder", true, "Folder where the rolling files will be saved.");
        folder.setRequired(true);
        folder.setType(String.class);
        options.addOption(folder);

        Option timeout = new Option("t", "timeout", true, "Time it takes to rollover files in seconds. Default 1 day.");
        timeout.setRequired(false);
        timeout.setType(int.class);
        options.addOption(timeout);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        Configuration config = new Configuration();
        if (cmd.hasOption("timeout")) {
            config.setRolling_timeout_ms(Integer.parseInt(cmd.getOptionValue("timeout"))*1000);
        } else {
            config.setRolling_timeout_ms(1000 * 60 * 60 * 24);
        }
        if (cmd.hasOption("port")) {
            config.setPort(Integer.parseInt(cmd.getOptionValue("port")));
        } else {
            config.setPort(8090);
        }
        config.setFolder(cmd.getOptionValue("folder"));
        HTTPServer server = new HTTPServer(config, new ServletDependenciesModule(), new DependenciesModule(config));
        server.start();
    }

}
