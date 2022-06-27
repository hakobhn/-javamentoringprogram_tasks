package com.epam.multithreading.training.task4;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ArrayObjectPool implements BlockingObjectPool {

    private Queue<Object> queue;

    private int capacity;
    private int size = 0;

    /**
     * Creates filled pool of passed size
     *
     * Class ArrayDeque<E> Resizable-array implementation of the Deque interface.
     * Array deques have no capacity restrictions; they grow as necessary to support usage.
     * They are not thread-safe;
     *
     * @param size of pool
     */
    public ArrayObjectPool(int size) {
        this.capacity = size;
        queue = new ArrayDeque<>(size);
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
                Object obj = queue.remove();
                size--;
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
                while (size == capacity)
                    queue.wait(); //wait for the queue to become with free space
                queue.offer(object);
                size++;
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
