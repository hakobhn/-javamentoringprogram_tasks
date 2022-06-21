package com.epam.multithreading.training.task6.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockProducer implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(LockProducer.class);

    private Queue<Integer> buffer;

    private Duration duration;

    private Random random = new Random();
    private Lock aLock;
    private Condition bufferNotFull;
    private Condition bufferNotEmpty;

    public LockProducer(Queue<Integer> buffer, Duration duration, Lock aLock, Condition bufferNotFull, Condition bufferNotEmpty) {
        this.buffer = buffer;
        this.duration = duration;
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
                while (buffer.size() == 5)
                    bufferNotFull.await();

                buffer.offer(random.nextInt(10));
                logger.info("Buffer: {}", buffer);

                bufferNotEmpty.signalAll();

            } catch (Exception e) {
                logger.error("Producer caught error: {}", e.getLocalizedMessage());
                e.printStackTrace();
            } finally {
                aLock.unlock();
            }
        }

    }
}
