package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExchangeProcessor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeProcessor.class);

    private List<Exchange> exchanges = new ArrayList<>();
    private Map<Currency, BigDecimal> rates = new HashMap<>();


    @Override
    public void run() {

        ExchangeRates exchangeRate = new ExchangeRates(rates);
        exchangeRate.start();


        ExchangeGenerator exchangeGenerator = new ExchangeGenerator(rates, exchanges);
        exchangeGenerator.start();

        try {
            exchangeRate.join();
            exchangeGenerator.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (true) {

            ExecutorService executorService = Executors.newScheduledThreadPool(5);

            exchanges.stream().forEach(
                    exchange -> {
                        Future<BigDecimal> amount = executorService.submit(exchange);
                        logger.info("Exchanged amounts: {}", amount);
                    }
            );

            //shut down the executor service now
            logger.info("shut down the executor service");
            executorService.shutdown();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
