package com.epam.multicurrency.training.task2;


import com.epam.multicurrency.training.task1.MapReadWriteManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ConcurrentModificationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionReadWriteManagerTest {

    private static Logger logger = LoggerFactory.getLogger(CollectionReadWriteManagerTest.class);

    @Test
    void testManagerInit() {
        CollectionReadWriteManager manager = new CollectionReadWriteManager();
        try {
            manager.processReadAndWrite();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCalcSqrtOfCollectionSum() {
        assertEquals(NumberUtil.calcSqrtOfCollectionSum(List.of(1,2,5)), 5.477);
    }

}
