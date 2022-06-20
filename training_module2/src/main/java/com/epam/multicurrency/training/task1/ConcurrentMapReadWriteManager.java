package com.epam.multicurrency.training.task1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(ConcurrentMapReadWriteManager.class);

    public ConcurrentMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration) {
        super(maxVal, waitToAdd, waitToRead, processingDuration);
        storage = new ConcurrentHashMap<>();
    }


}
