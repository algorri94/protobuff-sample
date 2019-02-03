package org.algorrim.protobuff.sample.persistence;

import org.algorrim.protobuff.sample.model.SimpleObject;

/**
 * Interface to persist an object.
 */
public interface IPersistence {

    /**
     * Saves a simple object to the underlying persistence layer.
     * @param simpleObject A simple object to be saved.
     */
    void saveSimpleObject(SimpleObject simpleObject) throws PersistenceException;

}
