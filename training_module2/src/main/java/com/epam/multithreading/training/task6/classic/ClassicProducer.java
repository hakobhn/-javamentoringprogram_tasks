package com.epam.multithreading.training.task6.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.Random;

public class ClassicProducer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ClassicProducer.class);

    private Queue<Integer> buffer;

    private Random random = new Random();

    private Duration duration;

    public ClassicProducer(Queue<Integer> buffer, Duration duration) {
        this.buffer = buffer;
        this.duration = duration;
    }

    @Override
    public void run() {
        var startTime = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(startTime.plus(duration))) {
            try {
                synchronized (buffer) {
                    while (buffer.size() == 5)
                        buffer.wait();

                    buffer.offer(random.nextInt(10));
                    buffer.notify();

                    logger.info("Buffer: {}", buffer);
                }
//                Thread.sleep(1000);

            } catch (Exception e) {
                logger.error("Producer caught error: {}", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

    }
}
