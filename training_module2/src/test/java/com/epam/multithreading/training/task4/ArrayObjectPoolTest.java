package com.epam.multithreading.training.task4;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArrayObjectPoolTest {

    private static Logger logger = LoggerFactory.getLogger(ArrayObjectPoolTest.class);

    @Test
    void testBlockingObjectQueue() {
        BlockingObjectPool pool = new ArrayObjectPool(10);
        assertNotNull(pool.get());

        Thread adder = new Thread(() -> {
            while (true) {
                pool.take(new Object());
                logger.info("Adder Poll: {}", pool);
            }
        });
        adder.start();

        Thread remover = new Thread(() -> {
            while (true) {
                pool.get();
                logger.info("Remover Poll: {}", pool);
            }
        });
        remover.start();

        try {
            adder.join();
            remover.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
