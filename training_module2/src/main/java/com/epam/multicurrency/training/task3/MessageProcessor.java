package com.epam.multicurrency.training.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MessageProcessor {

    private static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    private int waitTime = 100;
    private Random random = new Random();

    public MessageProcessor(int waitTime) {
        this.waitTime = waitTime;
    }

    public void execute() throws Exception {
        var topics = List.of("Topic1","Topic2","Topic3","Topic4").stream()
                .collect(Collectors.toMap(Object::toString, st -> new LinkedList<String>()));

        final MessageBus bus = new MessageBus(topics);

        List<Thread> producersList = new ArrayList<>();
        List<Thread> consumersList = new ArrayList<>();

        // Create producer thread
        for (String tpc : topics.keySet()) {
            // Create producer threads
            Thread[] producers = new Thread[random.nextInt(3) + 1];
            for (int i = 0; i < producers.length; i++) {
                producers[i] = new Thread(() -> {
                    try {
                        bus.produce(tpc, waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("Topic: {}", tpc);
                });
                producers[i].start();
            }
            producersList.addAll(Arrays.asList(producers));

            // Create consumer threads
            Thread[] consumers = new Thread[random.nextInt(3) + 1];
            for (int i = 0; i < consumers.length; i++) {
                consumers[i] = new Thread(() -> {
                    try {
                        bus.consume(tpc, waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("Topic: {}", tpc);
                });
                consumers[i].start();
            }

            consumersList.addAll(Arrays.asList(consumers));
        }

        for (Thread producer : producersList) {
            try {
                producer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        for (Thread consumer : consumersList) {
            try {
                consumer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
