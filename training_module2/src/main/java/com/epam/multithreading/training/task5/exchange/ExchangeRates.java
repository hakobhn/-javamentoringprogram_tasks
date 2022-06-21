package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ExchangeRates extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRates.class);

    private static final double MAX_RATE = 2.0;

    private Map<Currency, BigDecimal> rates;

    private Random random = new Random();

    public ExchangeRates(Map<Currency, BigDecimal> rates) {
        this.rates = rates;

        Arrays.stream(Currency.values()).forEach(
                                cur -> {
                                    if (cur.equals(Currency.USD)) {
                                        rates.put(cur, BigDecimal.valueOf(1.0));
                                    }
                                    BigDecimal percent = BigDecimal.valueOf(MAX_RATE*2*(random.nextDouble() - 0.5))
                                            .setScale(1, RoundingMode.HALF_UP);
                                    rates.put(cur, BigDecimal.valueOf(cur.getValue() + cur.getValue()*percent.doubleValue()/100.0));
                                });
    }

    @Override
    public void run() {
        while (true) {
            Arrays.stream(Currency.values()).forEach(
            cur -> {
                if (cur.equals(Currency.USD)) {
                    rates.put(cur, BigDecimal.valueOf(1.0));
                }
                BigDecimal percent = BigDecimal.valueOf(MAX_RATE*2*(random.nextDouble() - 0.5))
                        .setScale(1, RoundingMode.HALF_UP);
                rates.put(cur, BigDecimal.valueOf(cur.getValue() + cur.getValue()*percent.doubleValue()/100.0));
            });
            logger.info("Rates: " + rates);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
