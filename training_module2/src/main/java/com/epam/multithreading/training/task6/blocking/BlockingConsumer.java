package com.epam.multithreading.training.task6.blocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class BlockingConsumer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BlockingConsumer.class);

    BlockingQueue<Integer> buffer;
    private List<Integer> data;
    private Duration duration;

    public BlockingConsumer(BlockingQueue<Integer> buffer, Duration duration, List<Integer> data) {
        this.buffer = buffer;
        this.duration = duration;
        this.data = data;
    }

    @Override
    public void run() {
        var startTime = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(startTime.plus(duration))) {
            try {
                Integer val = buffer.take();
                data.add(val);

                logger.info("Consumed value: {}", val);

                logger.info("Data: {}", data);
                logger.info("Average: {}", getAverage());

            } catch (Exception e) {
                logger.error("Consumer caught error: {}", e.getLocalizedMessage());
            }
        }
    }

    private double getAverage() {
        return data.stream().reduce(0, (a, b) -> a + b).doubleValue()/data.size();
    }
}
