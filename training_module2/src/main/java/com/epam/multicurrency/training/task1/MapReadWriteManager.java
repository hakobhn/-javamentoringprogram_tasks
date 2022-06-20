package com.epam.multicurrency.training.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(MapReadWriteManager.class);

    private int maxVal = 10;
    private int waitToAdd = 100;
    private int waitToRead = 1000;
    private Duration processingDuration = Duration.of(10, ChronoUnit.SECONDS);

    private Map<Integer, Integer> storage = new HashMap<>();
    private Integer sum = 0;
    private Random random = new Random();

    private Throwable error = null;

    public MapReadWriteManager() {
    }

    public MapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration) {
        this.maxVal = maxVal;
        this.waitToAdd = waitToAdd;
        this.waitToRead = waitToRead;
        this.processingDuration = processingDuration;
    }

    public void processReadAndWrite() throws Exception {
        var startTime = LocalDateTime.now();
         Thread writer = new Thread( () -> {
             while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                 try {
                     Thread.sleep(waitToAdd);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
                 storage.putIfAbsent(random.nextInt(1000000), random.nextInt(maxVal));
             }
         });
         Thread reader = new Thread( () -> {
             while (LocalDateTime.now().isBefore(startTime.plus(processingDuration))) {
                 try {
                     Thread.sleep(waitToRead);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }

                 sum = storage.values().stream().reduce(0, (a, b) -> a + b);

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

    ;

    public Map<Integer, Integer> getStorage() {
        return storage;
    }


    public Integer getSum() {
        return sum;
    }

    public Throwable getError() {
        return error;
    }
}
