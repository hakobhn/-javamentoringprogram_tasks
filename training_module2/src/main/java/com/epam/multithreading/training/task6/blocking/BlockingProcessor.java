package com.epam.multithreading.training.task6.blocking;

import com.epam.multithreading.training.task6.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingProcessor extends Processor {

    private Duration duration;

    public BlockingProcessor(Duration duration) {
        this.duration = duration;
    }

    @Override
    public List<Integer> call() throws InterruptedException {

        BlockingQueue<Integer> buffer = new LinkedBlockingDeque<>(5);

        Thread producer = new Thread(new BlockingProducer(buffer, duration));
        Thread consumer = new Thread(new BlockingConsumer(buffer, duration, data));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        return data;
    }
}
