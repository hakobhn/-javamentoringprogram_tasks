package com.epam.multicurrency.training.task1;

import com.epam.multicurrency.training.task1.map.ThreadSafeSynchronizedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ThreadSafeSynchronizedMapReadWriteManager extends MapReadWriteManager {

    private static Logger logger = LoggerFactory.getLogger(ThreadSafeSynchronizedMapReadWriteManager.class);

    public ThreadSafeSynchronizedMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead,
                                                     Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = new ThreadSafeSynchronizedMap<>();
    }


}
