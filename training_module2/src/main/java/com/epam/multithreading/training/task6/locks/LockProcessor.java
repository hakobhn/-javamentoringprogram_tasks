package com.epam.multithreading.training.task6.locks;

import com.epam.multithreading.training.task6.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockProcessor extends Processor {

    private final static Logger logger = LoggerFactory.getLogger(LockProcessor.class);

    private Duration duration;

    public LockProcessor(Duration duration) {
        this.duration = duration;
    }

    @Override
    public List<Integer> call() throws InterruptedException {

        Queue<Integer> buffer = new PriorityQueue<>(5);

        Lock aLock = new ReentrantLock();
        Condition bufferNotFull = aLock.newCondition();
        Condition bufferNotEmpty = aLock.newCondition();

        Thread producer = new Thread(new LockProducer(buffer, duration, aLock, bufferNotFull, bufferNotEmpty));
        Thread consumer = new Thread(new LockConsumer(buffer, duration, data, aLock, bufferNotFull, bufferNotEmpty));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        return data;
    }
}
