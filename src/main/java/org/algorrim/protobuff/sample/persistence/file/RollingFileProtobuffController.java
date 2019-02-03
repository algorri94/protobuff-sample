package org.algorrim.protobuff.sample.persistence.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.algorrim.protobuff.sample.persistence.IController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Implementation of the controller interface using a synchronized queue.
 */
@Singleton
public class RollingFileProtobuffController implements IController {

    private BlockingQueue<byte[]> queue;

    @Inject
    private RollingFileProtobuffController() {
        queue = new ArrayBlockingQueue<byte[]>(1000);
    }

    public void putWrite(byte[] data) throws InterruptedException {
        queue.put(data);
    }

    public byte[] getWrite() throws InterruptedException {
        return queue.take();
    }
}
