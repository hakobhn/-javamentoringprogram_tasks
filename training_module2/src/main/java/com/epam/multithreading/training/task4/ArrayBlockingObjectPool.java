package com.epam.multithreading.training.task4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingObjectPool implements BlockingObjectPool {

    private BlockingQueue<Object> queue;

    /**
     * Creates filled pool of passed size
     *
     * BlockingQueue implementations are thread-safe.
     * All queuing methods achieve their effects atomically using internal locks
     * or other forms of concurrency control.
     *
     * @param size of pool
     */
    public ArrayBlockingObjectPool(int size) {
        queue = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            queue.offer(new Object());
        }
    }

    @Override
    public Object get() {
        try {
            Object obj = queue.take();
            return obj;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void take(Object object) {
        queue.offer(object);
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
