package org.algorrim.protobuff.sample.persistence.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.algorrim.protobuff.sample.persistence.IController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Thread that consumes from the controller and saves the data to a rolling file.
 */
@Singleton
public class RollingFileProtobuffConsumer implements Runnable{

    private static Logger log = LogManager.getLogger(RollingFileProtobuffConsumer.class);
    private IController controller;
    private int timeout_ms;
    private long lastExecution;
    private String fileFolder;
    private int counter = 0;
    private FileOutputStream file;
    private Thread thread;

    @Inject
    private RollingFileProtobuffConsumer(IController controller, @Named("rolling-timeout-ms") int timeout_ms,
                                        @Named("file-folder") String fileFolder) throws FileNotFoundException {
        this.controller = controller;
        this.timeout_ms = timeout_ms;
        this.fileFolder = fileFolder;

        lastExecution = System.currentTimeMillis();
        file = new FileOutputStream(getFile());
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Polls the controller constantly for data and saves it to a rolling file.
     */
    public void run() {
        log.info("The RollingFileConsumer thread started running");
        while (true) {
            try {
                saveToRollingFile(controller.getWrite());
            } catch (InterruptedException e) {
                log.error("Couldn't read data to write from the controller.");
                log.error("Exception: ", e);
            } catch (IOException e) {
                log.error("Couldn't write the data to the file. ");
                log.error("Exception: ", e);
            }
        }
    }

    /**
     * Writes to rolling file the array of bytes given as parameter. In order to delimit
     * each array of bytes an integer is written before with the length of the byte array.
     * @param data Array of bytes to be written
     * @throws IOException
     */
    private void saveToRollingFile(byte[] data) throws IOException {
        long currTime = System.currentTimeMillis();
        if( (currTime-lastExecution) > timeout_ms) {
            lastExecution = currTime;
            counter++;
            file.close();
            file = new FileOutputStream(getFile());
        }
        log.debug("Saving data [" + Arrays.toString(data) + "] to the file " + getFile() + ".");
        file.write(data.length);
        file.write(data);
        log.debug("Data saved successfully to the file.");
    }

    /**
     * Returns the current filename.
     * @return The filename.
     */
    private File getFile() {
        return new File(fileFolder, "simpleobject."+counter+".bin");
    }

    /**
     * Closes the file and stops the thread running the consumer.
     * @throws IOException
     */
    public void stop() throws IOException {
        thread.stop();
        file.close();
    }
}
