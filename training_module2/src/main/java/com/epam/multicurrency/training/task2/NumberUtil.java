package com.epam.multicurrency.training.task2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class NumberUtil {

    private final static Logger logger = LoggerFactory.getLogger(NumberUtil.class);

    private LinkedList<Integer> collection = new LinkedList<>();
    private LinkedList<Integer> bufferSum = new LinkedList<>();
    private LinkedList<Integer> bufferCalc = new LinkedList<>();

    private int capacity = 5;
    private Random random = new Random();

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                logger.info("------------------------------");
                logger.info("Collection: {}", collection);
                logger.info("bufferSum: {}", bufferSum);
                logger.info("bufferCalc: {}", bufferCalc);

                while (bufferSum.size() == capacity)
                    wait();

                Integer value = random.nextInt(100);
                logger.info("Producer produced: {}", value);
                bufferSum.offer(value);

                // notifies the consumer threads that
                // now it can start consuming
                notify();

                // Sleep for slow execution
                Thread.sleep(100);
            }
        }
    }

    public void consumeForSum() throws InterruptedException {
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
                Thread.sleep(100);
            }
        }
    }

    public void consumeForCalc() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (bufferCalc.size() == 0)
                    wait();

                int val = bufferCalc.removeFirst();

                collection.add(val);
                logger.info("Calc consumer consumed value: {}, calc is: {}", val, calcSqrtOfCollectionSum(collection));

                notify();
                Thread.sleep(100);
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
