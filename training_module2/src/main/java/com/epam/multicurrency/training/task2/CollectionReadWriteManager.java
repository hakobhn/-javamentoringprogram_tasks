package com.epam.multicurrency.training.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(CollectionReadWriteManager.class);

    private int waitTime = 100;

    public CollectionReadWriteManager(int waitTime) {
        this.waitTime = waitTime;
    }

    public void processReadAndWrite() throws Exception {
        // Initiating number processor
        final NumberProcessor processor = new NumberProcessor();

        // Create producer thread
        Thread producer = new Thread(() -> {
            try {
                processor.produce(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create consumer thread for calculating sum
        Thread sumConsumer = new Thread(() -> {
            try {
                processor.consumeForSum(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create consumer thread for calculating function
        Thread calcConsumer = new Thread(() -> {
            try {
                processor.consumeForCalc(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start all threads
        logger.info("Starting all threads...");
        producer.start();
        sumConsumer.start();
        calcConsumer.start();

        producer.join();
        sumConsumer.join();
        calcConsumer.join();
    }

}
