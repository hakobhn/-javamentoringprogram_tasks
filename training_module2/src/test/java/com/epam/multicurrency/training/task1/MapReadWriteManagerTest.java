package com.epam.multicurrency.training.task1;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

public class MapReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(MapReadWriteManagerTest.class);

    @Test
    public void testManagerInit() {
        MapReadWriteManager simpleManager = new SimpleMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS));

        MapReadWriteManager concurentManager = new SimpleMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS));

        MapReadWriteManager synchronizedManager = new SynchronizedMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS));

        assertTrue(simpleManager.getStorage().isEmpty());
        try {
            simpleManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(simpleManager.getStorage().isEmpty());

        assertTrue(concurentManager.getStorage().isEmpty());
        try {
            concurentManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(concurentManager.getStorage().isEmpty());

        assertTrue(synchronizedManager.getStorage().isEmpty());
        try {
            synchronizedManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(synchronizedManager.getStorage().isEmpty());
    }


    @Test
    public void testManagerWithoutWaitFails() {

        try {
            MapReadWriteManager simpleManager = new SimpleMapReadWriteManager(10, 5, 5, Duration.of(1, ChronoUnit.SECONDS));
            simpleManager.processReadAndWrite();
            assertTrue(simpleManager.getError().getCause().equals(ConcurrentModificationException.class));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            MapReadWriteManager concurrentManager = new ConcurrentMapReadWriteManager(10, 5, 5, Duration.of(1, ChronoUnit.SECONDS));
            concurrentManager.processReadAndWrite();
            assertNull(concurrentManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            MapReadWriteManager synchronizedManager = new SynchronizedMapReadWriteManager(10, 5, 5, Duration.of(1, ChronoUnit.SECONDS));
            synchronizedManager.processReadAndWrite();
            assertNull(synchronizedManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

}
