package com.epam.multicurrency.training.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

public class SynchronizedMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(SynchronizedMapReadWriteManager.class);

    public SynchronizedMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void processReadAndWrite() throws Exception {
        var startTime = LocalDateTime.now();
        Thread writer = new Thread(() -> {
            while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                if (waitToAdd > 0) {
                    try {
                        Thread.sleep(waitToAdd);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                synchronized (storage) {
                    storage.putIfAbsent(random.nextInt(Integer.MAX_VALUE), random.nextInt(maxVal));
                }
            }
        });
        Thread reader = new Thread(() -> {
            while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                if (waitToRead > 0) {
                    try {
                        Thread.sleep(waitToRead);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                synchronized (storage) {
                    sum = storage.values().stream().reduce(0, (a, b) -> a + b);

                    if (logs) {
                        logger.info("Map: {}", storage);
                        logger.info("Sum: {}", sum);
                    }
                }
            }
        });

        Thread.UncaughtExceptionHandler hWriter = (th, ex) -> {
            logger.error("In writer uncaught exception: {}", ex.getLocalizedMessage());
            if (logs) {
                ex.printStackTrace();
            }
            error = ex;
        };
        Thread.UncaughtExceptionHandler hReader = (th, ex) -> {
            logger.error("In reader uncaught exception: {}", ex.getLocalizedMessage());
            if (logs) {
                ex.printStackTrace();
            }
            error = ex;
        };

        if (logs) {
            logger.info("Starting writer thread...");
        }
        writer.setUncaughtExceptionHandler(hWriter);
        writer.start();
        if (logs) {
            logger.info("Starting reader thread...");
        }
        reader.setUncaughtExceptionHandler(hReader);
        reader.start();

        writer.join();
        reader.join();
    }
}
