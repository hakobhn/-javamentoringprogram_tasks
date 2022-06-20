package com.epam.multicurrency.training.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;

public class CollectionReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(CollectionReadWriteManager.class);

    public void processReadAndWrite() throws Exception {
        // Object of a class that has both produce()
        // and consume() methods
        final NumberUtil util = new NumberUtil();

        // Create producer thread
        Thread t1 = new Thread(() -> {
            try {
                util.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(() -> {
            try {
                util.consumeForSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create consumer thread
        Thread t3 = new Thread(() -> {
            try {
                util.consumeForCalc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start both threads
        t1.start();
        t2.start();
        t3.start();

        // t1 finishes before t2
        t1.join();
        t2.join();
        t3.join();
    }

}
