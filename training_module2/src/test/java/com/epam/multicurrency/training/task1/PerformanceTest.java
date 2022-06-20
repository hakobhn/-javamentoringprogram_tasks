package com.epam.multicurrency.training.task1;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class PerformanceTest {

    private static Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

    @Test
    public void testPerformanceOnGeneretedEntryCountInSameTime() {

        MapReadWriteManager simpleManager = new SimpleMapReadWriteManager(10, 0, 100,
                Duration.of(2, ChronoUnit.SECONDS), false);

        MapReadWriteManager concurrentManager = new ConcurrentMapReadWriteManager(10, 0, 100,
                Duration.of(2, ChronoUnit.SECONDS), false);
//
        MapReadWriteManager synchronizedManager = new SynchronizedMapReadWriteManager(10, 0, 100,
                Duration.of(2, ChronoUnit.SECONDS), false);

        MapReadWriteManager threadSafeManager = new ThreadSafeMapReadWriteManager(10, 0, 100,
                Duration.of(2, ChronoUnit.SECONDS), false);

        MapReadWriteManager threadSafeSynchronizedManager =
                new ThreadSafeSynchronizedMapReadWriteManager(10, 0, 100,
                Duration.of(2, ChronoUnit.SECONDS), false);

        try {
            simpleManager.processReadAndWrite();
            logger.info("\nSimple hashmap container case: {}", simpleManager.getStorage().values().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            concurrentManager.processReadAndWrite();
            logger.info("\nConcurrent hashmap container case: {}", concurrentManager.getStorage().values().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//
        try {
            synchronizedManager.processReadAndWrite();
            logger.info("\nCollections synchronized map container case: {}", synchronizedManager.getStorage().values().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            threadSafeManager.processReadAndWrite();
            logger.info("\nThreadSafe map container case: {}", threadSafeManager.getStorage().values().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            threadSafeSynchronizedManager.processReadAndWrite();
            logger.info("\nThreadSafeSynchronized map container case: {}", threadSafeSynchronizedManager.getStorage().values().size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
