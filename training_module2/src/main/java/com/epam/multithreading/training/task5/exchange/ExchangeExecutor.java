package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExchangeExecutor extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeExecutor.class);

    private List<Exchange> exchanges;

    public ExchangeExecutor(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public void run() {

        ExecutorService executorService = Executors.newScheduledThreadPool(5);

        while (true) {

            synchronized (exchanges) {
                exchanges.stream().forEach(
                        exchange -> {
                            Future<BigDecimal> amount = executorService.submit(exchange);
                            try {
                                logger.info("Exchanged amounts: {}", amount.get());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                logger.error("Unable to execute exchange. Error: {}", e.getLocalizedMessage());
                            }
                        }
                );
            }

//            //shut down the executor service now
//            logger.info("shut down the executor service");
//            executorService.shutdown();

            logger.info("Sleeping executor service...");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
