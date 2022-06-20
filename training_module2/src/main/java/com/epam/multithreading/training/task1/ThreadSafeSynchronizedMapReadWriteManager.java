package com.epam.multithreading.training.task1;

import com.epam.multithreading.training.task1.map.ThreadSafeSynchronizedMap;

import java.time.Duration;

public class ThreadSafeSynchronizedMapReadWriteManager extends MapReadWriteManager {

    public ThreadSafeSynchronizedMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead,
                                                     Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = new ThreadSafeSynchronizedMap<>();
    }


}
