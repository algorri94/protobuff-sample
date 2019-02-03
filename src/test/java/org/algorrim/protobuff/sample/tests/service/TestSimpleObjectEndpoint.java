package org.algorrim.protobuff.sample.tests.service;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.algorrim.protobuff.sample.model.SimpleObject;
import org.algorrim.protobuff.sample.persistence.IPersistence;
import org.algorrim.protobuff.sample.persistence.PersistenceException;
import org.algorrim.protobuff.sample.service.SimpleObjectEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestSimpleObjectEndpoint {

    private IPersistence persistence;
    private Injector injector;

    @Before
    public void init() {
        persistence = mock(IPersistence.class);
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IPersistence.class).toInstance(persistence);
            }
        });
    }

    @Test
    public void TestPostSimpleObject() throws PersistenceException {
        ArgumentCaptor<SimpleObject> valueCapture = ArgumentCaptor.forClass(SimpleObject.class);
        doNothing().when(persistence).saveSimpleObject(valueCapture.capture());
        SimpleObjectEndpoint endpoint = injector.getInstance(SimpleObjectEndpoint.class);

        SimpleObject so = new SimpleObject();
        so.setId(1);
        so.setName("ABC");
        endpoint.postSimpleObject(so);

        assertEquals(so, valueCapture.getValue());
    }

}
