package com.epam.multithreading.training.task1;

import com.epam.multithreading.training.task1.map.ThreadSafeMap;

import java.time.Duration;

public class ThreadSafeMapReadWriteManager extends MapReadWriteManager {

    public ThreadSafeMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = new ThreadSafeMap<>();
    }


}
