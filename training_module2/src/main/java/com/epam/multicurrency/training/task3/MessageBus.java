package com.epam.multicurrency.training.task3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MessageBus {

    private final static Logger logger = LoggerFactory.getLogger(MessageBus.class);

    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private Map<String, LinkedList<String>> topics;

    public MessageBus(Map<String, LinkedList<String>> topics) {
        this.topics = topics;
    }

    private int capacity = 5;
    private Random random = new Random();

    public void produce(String topic, int waitTime) throws InterruptedException {
        logger.info("Starting producer for topic: {}", topic);
        while (true) {
            synchronized (this) {
                List<String> messages = topics.get(topic);
                while (messages.size() == capacity)
                    wait();
                String message = generateMessage(random);
                messages.add(message);
                logger.info("Produced value: {}", message );

                // notifies the consumer threads that
                notify();

                // Sleep for slow execution
                Thread.sleep(waitTime);
            }
        }
    }

    public void consume(String topic, int waitTime) throws InterruptedException {
        logger.info("Starting consumer for topic: {}", topic);
        while (true) {
            synchronized (this) {
                LinkedList<String> messages = topics.get(topic);
                while (messages.size() == 0)
                    wait();

                String val = messages.removeFirst();
                logger.info("Consumed value: {}", val );
                logger.info("topic: {}, messages: {}", topic, messages );

                notify();
                Thread.sleep(waitTime);
            }
        }
    }

    public static String generateMessage(Random random) {
        int wordCount = random.nextInt(8)+2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            int lettersCount = random.nextInt(5)+2;
            char[] word = new char[lettersCount];
            for (int j = 0; j < lettersCount; j++) {
                word[j] = alphabet.charAt(random.nextInt(alphabet.length()-1));
            }
            if ( i == 0) {
                word[0] = Character.toUpperCase(word[0]);
            }
            sb.append(word);
            if( i == wordCount - 1) {
                sb.append(".");
            } else {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
