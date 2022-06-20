package com.epam.multicurrency.training.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SimpleMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(SimpleMapReadWriteManager.class);

    public SimpleMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration) {
        super(maxVal, waitToAdd, waitToRead, processingDuration);
        storage = new HashMap<>();
    }


}
