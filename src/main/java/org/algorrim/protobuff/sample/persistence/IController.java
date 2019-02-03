package org.algorrim.protobuff.sample.persistence;

/**
 * Controller interface for Consumer-Producer pattern. It takes care of the communication between the producer
 * and the consumer so that the producer can drop in the queue the data to be written so it gets unlocked and it can
 * continue its execution.
 */
public interface IController {

    /**
     * Puts the array of bytes to be written into a queue. The producer should call this method.
     * @param data The array of bytes to be written
     * @throws InterruptedException
     */
    void putWrite(byte [] data) throws InterruptedException;

    /**
     * Gets an array of bytes from the queue in order to be written. The consumer should call this method.
     * @return An array of bytes
     * @throws InterruptedException
     */
    byte[] getWrite() throws InterruptedException;

}
