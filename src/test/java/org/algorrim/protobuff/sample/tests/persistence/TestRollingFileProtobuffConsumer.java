package org.algorrim.protobuff.sample.tests.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.algorrim.protobuff.sample.model.SimpleObjectOuterClass;
import org.algorrim.protobuff.sample.persistence.IController;
import org.algorrim.protobuff.sample.persistence.file.RollingFileProtobuffConsumer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestRollingFileProtobuffConsumer {

    private IController controllerMock;
    private Injector injector;

    @Before
    public void init() {
        controllerMock = mock(IController.class);
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IController.class).toInstance(controllerMock);
                bind(int.class).annotatedWith(Names.named("rolling-timeout-ms")).toInstance(1000);
                bind(String.class).annotatedWith(Names.named("file-folder")).toInstance(System.getProperty("user.dir"));
            }
        });
    }

    @Test
    public void TestWriteToFile() throws InterruptedException, IOException {
        SimpleObjectOuterClass.SimpleObject expected = SimpleObjectOuterClass.SimpleObject.newBuilder()
                .setId(1)
                .setName("ABC")
                .build();
        when(controllerMock.getWrite()).thenReturn(expected.toByteArray());
        RollingFileProtobuffConsumer consumer = injector.getInstance(RollingFileProtobuffConsumer.class);
        Thread.sleep(50);
        consumer.stop();
        SimpleObjectOuterClass.SimpleObject actual = readObjectFromFile(new File(System.getProperty("user.dir"),"simpleobject.0.bin"));
        assertEquals(expected, actual);
    }

    @Test
    public void TestRollOver() throws InterruptedException, IOException {
        SimpleObjectOuterClass.SimpleObject expected1 = SimpleObjectOuterClass.SimpleObject.newBuilder()
                .setId(1)
                .setName("ABC")
                .build();
        SimpleObjectOuterClass.SimpleObject expected2 = SimpleObjectOuterClass.SimpleObject.newBuilder()
                .setId(2)
                .setName("DEF")
                .build();
        when(controllerMock.getWrite()).thenReturn(expected1.toByteArray(), expected2.toByteArray());
        RollingFileProtobuffConsumer consumer = injector.getInstance(RollingFileProtobuffConsumer.class);
        Thread.sleep(1100);
        consumer.stop();
        SimpleObjectOuterClass.SimpleObject actual1 = readObjectFromFile(new File(System.getProperty("user.dir"),"simpleobject.0.bin"));
        assertEquals(expected1, actual1);
        SimpleObjectOuterClass.SimpleObject actual2 = readObjectFromFile(new File(System.getProperty("user.dir"),"simpleobject.1.bin"));
        assertEquals(expected2, actual2);
    }

    private SimpleObjectOuterClass.SimpleObject readObjectFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        int size = fis.read();
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();
        file.delete();
        return SimpleObjectOuterClass.SimpleObject.parseFrom(buffer);
    }
}
