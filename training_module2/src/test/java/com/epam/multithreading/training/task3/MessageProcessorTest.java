package com.epam.multithreading.training.task3;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageProcessorTest {

    private static Logger logger = LoggerFactory.getLogger(MessageProcessorTest.class);

    @Test
    void testGeneratingMessage() {
        MessageProcessor processor = new MessageProcessor(1000);
        try {
            processor.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
