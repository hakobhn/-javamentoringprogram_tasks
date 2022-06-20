package com.epam.multicurrency.training.task1;

import com.epam.multicurrency.training.task1.map.ThreadSafeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ThreadSafeMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(ThreadSafeMapReadWriteManager.class);

    public ThreadSafeMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = new ThreadSafeMap<>();
    }


}
