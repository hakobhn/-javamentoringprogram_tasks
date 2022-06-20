package com.epam.multicurrency.training.task1;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class OwnMapReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(OwnMapReadWriteManagerTest.class);

    @Test
    public void testOwnImplementedManagersInit() {
        MapReadWriteManager threadSafeManager = new ThreadSafeMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS), true);

        MapReadWriteManager threadSafeSynchronizedManager =
                new ThreadSafeSynchronizedMapReadWriteManager(10, 100, 1000,
                Duration.of(5, ChronoUnit.SECONDS), true);

        assertTrue(threadSafeManager.getStorage().isEmpty());
        try {
            threadSafeManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(threadSafeManager.getStorage().isEmpty());

        assertTrue(threadSafeSynchronizedManager.getStorage().isEmpty());
        try {
            threadSafeSynchronizedManager.processReadAndWrite();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertFalse(threadSafeSynchronizedManager.getStorage().isEmpty());
    }


    @Test
    public void testOwnManagersWithoutWaitFails() {

        try {
            MapReadWriteManager threadSafeManager = new ThreadSafeMapReadWriteManager(
                    10, 5, 5, Duration.of(1, ChronoUnit.SECONDS), true);
            threadSafeManager.processReadAndWrite();
            assertNull(threadSafeManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            MapReadWriteManager threadSafeSynchronizedManager = new ThreadSafeSynchronizedMapReadWriteManager(
                    10, 5, 5, Duration.of(1, ChronoUnit.SECONDS), true);
            threadSafeSynchronizedManager.processReadAndWrite();
            assertNull(threadSafeSynchronizedManager.getError());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

}
