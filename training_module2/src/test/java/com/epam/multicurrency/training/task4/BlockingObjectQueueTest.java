package com.epam.multicurrency.training.task4;


import com.epam.multicurrency.training.task3.MessageBus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BlockingObjectPoolTest {

    private static Logger logger = LoggerFactory.getLogger(BlockingObjectPoolTest.class);

    @Test
    void testBlockingObjectQueue() {
        BlockingObjectPool pool = new ArrayBlockingObjectPool(10);
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
