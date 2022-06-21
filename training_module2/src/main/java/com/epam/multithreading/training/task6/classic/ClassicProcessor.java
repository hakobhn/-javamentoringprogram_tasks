package com.epam.multithreading.training.task6.classic;

import com.epam.multithreading.training.task6.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClassicProcessor extends Processor {

    private final static Logger logger = LoggerFactory.getLogger(ClassicProcessor.class);

    private Duration duration;
    public ClassicProcessor(Duration duration) {
        this.duration = duration;
    }

    @Override
    public List<Integer> call() throws InterruptedException {

        Queue<Integer> buffer = new PriorityQueue<>(5);

        Thread producer = new Thread(new ClassicProducer(buffer, duration));
        Thread consumer = new Thread(new ClassicConsumer(buffer, duration, data));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        return data;
    }
}
