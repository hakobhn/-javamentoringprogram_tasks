package com.epam.multithreading.training.task4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingObjectPool implements BlockingObjectPool {

    private BlockingQueue<Object> queue;

    /**
     * Creates filled pool of passed size
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
        synchronized (queue) {
            try {
                while (queue.isEmpty())
                    queue.wait(); //wait for the queue to become not empty
                Object obj = queue.take();
                queue.notify();
                return obj;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void take(Object object) {
        synchronized (queue) {
            try {
                while (queue.remainingCapacity() == 0)
                    queue.wait(); //wait for the queue to become with free space
                queue.offer(object);
                queue.notify();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
