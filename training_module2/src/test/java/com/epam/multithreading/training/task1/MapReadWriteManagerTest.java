package com.epam.multithreading.training.task1;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class MapReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(MapReadWriteManagerTest.class);

    @Test
    void testManagersInit() {
        MapReadWriteManager simpleManager = new SimpleMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS), true);

        MapReadWriteManager concurrentManager = new SimpleMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS), true);

        MapReadWriteManager synchronizedManager = new SynchronizedMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS), true);

        assertTrue(simpleManager.getStorage().isEmpty());
        try {
            simpleManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(simpleManager.getStorage().isEmpty());

        assertTrue(concurrentManager.getStorage().isEmpty());
        try {
            concurrentManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(concurrentManager.getStorage().isEmpty());

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
    void testManagersWithoutWaitFails() {

        try {
            MapReadWriteManager simpleManager = new SimpleMapReadWriteManager(
                    10, 5, 5, Duration.of(1, ChronoUnit.SECONDS), true);
            simpleManager.processReadAndWrite();
            assertTrue(simpleManager.getError().getCause().equals(ConcurrentModificationException.class));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            MapReadWriteManager concurrentManager = new ConcurrentMapReadWriteManager(
                    10, 5, 5, Duration.of(1, ChronoUnit.SECONDS), true);
            concurrentManager.processReadAndWrite();
            assertNull(concurrentManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            MapReadWriteManager synchronizedManager = new SynchronizedMapReadWriteManager(
                    10, 5, 5, Duration.of(1, ChronoUnit.SECONDS), true);
            synchronizedManager.processReadAndWrite();
            assertNull(synchronizedManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

}
