package com.epam.multicurrency.training.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SynchronizedMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(SynchronizedMapReadWriteManager.class);

    public SynchronizedMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration) {
        super(maxVal, waitToAdd, waitToRead, processingDuration);
        storage = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void processReadAndWrite() throws Exception {
        var startTime = LocalDateTime.now();
        Thread writer = new Thread(() -> {
            while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                try {
                    Thread.sleep(waitToAdd);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (storage) {
                    storage.putIfAbsent(random.nextInt(1000000), random.nextInt(maxVal));
                }
            }
        });
        Thread reader = new Thread(() -> {
            while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                try {
                    Thread.sleep(waitToRead);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (storage) {
                    sum = storage.values().stream().reduce(0, (a, b) -> a + b);
                }

                logger.info("Map: " + storage);
                logger.info("Sum: " + sum);
            }
        });

        Thread.UncaughtExceptionHandler hWriter = (th, ex) -> {
            logger.error("Uncaught exception: " + ex);
            ex.printStackTrace();
            error = ex;
        };
        Thread.UncaughtExceptionHandler hReader = (th, ex) -> {
            logger.error("Uncaught exception: " + ex);
            ex.printStackTrace();
            error = ex;
        };

        logger.info("Starting writer thread...");
        writer.setUncaughtExceptionHandler(hWriter);
        writer.start();
        logger.info("Starting reader thread...");
        reader.setUncaughtExceptionHandler(hReader);
        reader.start();

        writer.join();
        reader.join();
    }
}
