package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExchangeProcessor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeProcessor.class);

    private List<Exchange> exchanges = Collections.synchronizedList(new ArrayList<>());
    private Map<Currency, BigDecimal> rates = new HashMap<>();


    @Override
    public void run() {

        ExchangeRates exchangeRate = new ExchangeRates(rates);
        exchangeRate.start();

        ExchangeGenerator exchangeGenerator = new ExchangeGenerator(rates, exchanges);
        exchangeGenerator.start();

        ExchangeExecutor exchangeExecutor = new ExchangeExecutor(exchanges);
        exchangeExecutor.start();

        try {
            exchangeRate.join();
            exchangeGenerator.join();
            exchangeExecutor.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
