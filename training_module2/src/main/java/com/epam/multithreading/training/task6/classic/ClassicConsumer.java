package com.epam.multithreading.training.task6.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ClassicConsumer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ClassicConsumer.class);

    Queue<Integer> buffer;
    private List<Integer> data;
    private Duration duration;

    public ClassicConsumer(Queue<Integer> buffer, Duration duration, List<Integer> data) {
        this.buffer = buffer;
        this.duration = duration;
        this.data = data;
    }

    @Override
    public void run() {
        var startTime = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(startTime.plus(duration))) {
            try {
                synchronized (buffer) {
                    while (buffer.size() == 0)
                        buffer.wait();

                    data.add(buffer.poll());
                    buffer.notify();

                    logger.info("Data: {}", data);
                    logger.info("Average: {}", getAverage());
                }

//                Thread.sleep(1000);

            } catch (Exception e) {
                logger.error("Consumer caught error: {}", e.getLocalizedMessage());
            }
        }
    }

    private double getAverage() {
        return data.stream().reduce(0, (a, b) -> a + b).doubleValue()/data.size();
    }
}
