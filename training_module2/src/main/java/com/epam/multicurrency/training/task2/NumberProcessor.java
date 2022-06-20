package com.epam.multicurrency.training.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class NumberProcessor {

    private final static Logger logger = LoggerFactory.getLogger(NumberProcessor.class);

    private LinkedList<Integer> collection = new LinkedList<>();
    private LinkedList<Integer> bufferSum = new LinkedList<>();
    private LinkedList<Integer> bufferCalc = new LinkedList<>();

    private int capacity = 5;
    private Random random = new Random();

    public void produce(int waitTime) throws InterruptedException {
        while (true) {
            synchronized (this) {
                logger.info("------------------------------");
                logger.info("Collection: {}", collection);
                logger.info("bufferSum: {}", bufferSum);
                logger.info("bufferCalc: {}", bufferCalc);

                while (bufferSum.size() == capacity)
                    wait();

                Integer value = random.nextInt(10);
                logger.info("Producer produced: {}", value);
                bufferSum.offer(value);

                // notifies the consumer threads that
                // now it can start consuming
                notify();

                // Sleep for slow execution
                Thread.sleep(waitTime);
            }
        }
    }

    public void consumeForSum(int waitTime) throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (bufferSum.size() == 0)
                    wait();

                int val = bufferSum.removeFirst();
                logger.info("Sum consumer consumed value: {}, sum is: {}", val, calcCollectionSum(collection) + val );

                while (bufferCalc.size() == capacity)
                    wait();
                bufferCalc.add(val);

                notify();
                Thread.sleep(waitTime);
            }
        }
    }

    public void consumeForCalc(int waitTime) throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (bufferCalc.size() == 0)
                    wait();

                int val = bufferCalc.removeFirst();

                collection.add(val);
                logger.info("Calc consumer consumed value: {}, calc is: {}", val, calcSqrtOfCollectionSum(collection));

                notify();
                Thread.sleep(waitTime);
            }
        }
    }

    public static Long calcCollectionSum(Collection<Integer> collection) {
        return collection.stream()
                        .reduce(0, (a,b)-> a+b).longValue();
    }

    public static Double calcSqrtOfCollectionSum(Collection<Integer> collection) {
        return BigDecimal.valueOf(Math.sqrt(collection.stream()
                .map(num -> Math.pow(num, 2))
                .reduce(0d, (a,b)-> a+b)
                .doubleValue()))
                .setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
}
