package com.epam.multithreading.training.task6.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConsumer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(LockConsumer.class);

    private Queue<Integer> buffer;
    private Duration duration;
    private List<Integer> data;

    private Lock aLock;
    private Condition bufferNotFull;
    private Condition bufferNotEmpty;

    public LockConsumer(Queue<Integer> buffer, Duration duration, List<Integer> data, Lock aLock, Condition bufferNotFull, Condition bufferNotEmpty) {
        this.buffer = buffer;
        this.duration = duration;
        this.data = data;
        this.aLock = aLock;
        this.bufferNotFull = bufferNotFull;
        this.bufferNotEmpty = bufferNotEmpty;
    }


    @Override
    public void run() {
        var startTime = LocalDateTime.now();
        while (LocalDateTime.now().isBefore(startTime.plus(duration))) {
            aLock.lock();
            try {
                synchronized (buffer) {
                    while (buffer.size() == 0)
                        bufferNotEmpty.await();

                    data.add(buffer.poll());
                    buffer.notify();

                    logger.info("Data: {}", data);
                    logger.info("Average: {}", getAverage());

                    bufferNotFull.signalAll();
                }

            } catch (Exception e) {
                logger.error("Consumer caught error: {}", e.getLocalizedMessage());
            } finally {
                aLock.unlock();
            }
        }
    }

    private double getAverage() {
        return data.stream().reduce(0, (a, b) -> a + b).doubleValue()/data.size();
    }
}
