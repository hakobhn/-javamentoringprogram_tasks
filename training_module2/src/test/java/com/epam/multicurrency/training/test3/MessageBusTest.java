package com.epam.multicurrency.training.test3;


import com.epam.multicurrency.training.task2.CollectionReadWriteManager;
import com.epam.multicurrency.training.task2.NumberProcessor;
import com.epam.multicurrency.training.task3.MessageBus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusTest {

    private static Logger logger = LoggerFactory.getLogger(MessageBusTest.class);


    @Test
    void testGeneratingMessage() {
        String message = MessageBus.generateMessage(new Random());
        logger.info("Generated message: {}", message);
        assertTrue(message.length() > 0);
        assertTrue(message.contains("."));
    }

}
