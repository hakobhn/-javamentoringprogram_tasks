package com.epam.multithreading.training.task6.blocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingProducer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(BlockingProducer.class);

    private BlockingQueue<Integer> buffer;

    private Random random = new Random();

    private Duration duration;

    public BlockingProducer(BlockingQueue<Integer> buffer, Duration duration) {
        this.buffer = buffer;
        this.duration = duration;
    }

    @Override
    public void run() {
        var startTime = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(startTime.plus(duration))) {
            try {
                Integer val = random.nextInt(10);
                buffer.offer(val, 500, TimeUnit.MILLISECONDS);
                logger.info("Produced value: {}", val);
            } catch (Exception e) {
                logger.error("Producer caught error: {}", e.getLocalizedMessage());
            }
        }

    }
}
