package com.epam.multicurrency.training.task1;

import java.time.Duration;
import java.util.HashMap;

public class SimpleMapReadWriteManager extends MapReadWriteManager {
    public SimpleMapReadWriteManager(int maxVal, int waitToAdd, int waitToRead, Duration processingDuration, boolean logs) {
        super(maxVal, waitToAdd, waitToRead, processingDuration, logs);
        storage = new HashMap<>();
    }


}
