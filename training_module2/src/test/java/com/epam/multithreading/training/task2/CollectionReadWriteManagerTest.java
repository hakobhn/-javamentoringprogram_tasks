package com.epam.multithreading.training.task2;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(CollectionReadWriteManagerTest.class);

    @Test
    void testNumberProcessorFunctionality() {
        logger.info("Starting collection read/write manager...");
        CollectionReadWriteManager manager = new CollectionReadWriteManager(100);
        try {
            manager.processReadAndWrite();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNumberProcessorFunctionalityForDeadlock() {
        logger.info("Starting collection read/write manager...");
        CollectionReadWriteManager manager = new CollectionReadWriteManager(0);
        try {
            manager.processReadAndWrite();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCalcSqrtOfCollectionSum() {
        assertEquals(NumberProcessor.calcSqrtOfCollectionSum(List.of(1,2,5)), 5.477);
    }

}
