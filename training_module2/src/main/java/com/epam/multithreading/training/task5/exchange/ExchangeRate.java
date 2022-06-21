package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ExchangeRate extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ExchangeRate.class);

    private static final double MAX_RATE = 2.0;

    private Map<Currency, BigDecimal> rates = new HashMap<>();

    private Random random = new Random();

    @Override
    public void run() {
        while (true) {
            rates = Arrays.stream(Currency.values())
                    .collect(
                            Collectors.toMap(
                                    cur -> cur,
                                    cur -> {
                                        if (cur.equals(Currency.USD)) {
                                            return BigDecimal.valueOf(1.0);
                                        }
                                        BigDecimal percent = BigDecimal.valueOf(MAX_RATE*2*(random.nextDouble() - 0.5))
                                                .setScale(1, RoundingMode.HALF_UP);
                                        return BigDecimal.valueOf(cur.getValue() + cur.getValue()*percent.doubleValue()/100.0);
                                    })
                    );
            logger.info("Rates: " + rates);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<Currency, BigDecimal> getRates() {
        return rates;
    }
}
