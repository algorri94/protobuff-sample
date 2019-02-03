package org.algorrim.protobuff.sample.persistence.file;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.algorrim.protobuff.sample.model.SimpleObject;
import org.algorrim.protobuff.sample.model.SimpleObjectOuterClass;
import org.algorrim.protobuff.sample.persistence.IController;
import org.algorrim.protobuff.sample.persistence.IPersistence;
import org.algorrim.protobuff.sample.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the IPersistence interface that persists the data to a protobuff rolling file. It acts
 * as the producer in the Producer-Consumer pattern.
 */
public class RollingFileProtobuffPersistence implements IPersistence {

    private static Logger log = LogManager.getLogger(RollingFileProtobuffPersistence.class);
    @Inject
    private IController writerController;
    @Inject @Named("consumer")
    private Runnable consumer;
    private final int retries = 3;

    public void saveSimpleObject(SimpleObject simpleObject) throws PersistenceException {
        log.debug("Saving [" + simpleObject + "]");
        SimpleObjectOuterClass.SimpleObject so = SimpleObjectOuterClass.SimpleObject.newBuilder()
                .setId(simpleObject.getId())
                .setName(simpleObject.getName())
                .build();
        saveAndRetryWhenFailure(so.toByteArray());
        log.debug("Dropped ["+simpleObject+"] to writing queue.");
    }

    private void saveAndRetryWhenFailure(byte [] data) throws PersistenceException {
        boolean failed = true;
        for(int i = 0; i<retries; i++) {
            try {
                log.debug("Trying to drop the data [" + data + "] to the queue. Retry: " + i);
                writerController.putWrite(data);
                failed = false;
                i = retries;
            } catch (InterruptedException e) {
                log.error("Couldn't send to the controller the SimpleObject to persist.");
                log.error("Exception: ", e);
            }
        }
        if (failed)
            throw new PersistenceException("Couldn't send data to the persistence controller after " + retries + " retries.");
    }

}
