package com.epam.multithreading.training.task6;

import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.model.CardType;
import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import com.epam.multithreading.training.task6.Processor;
import com.epam.multithreading.training.task6.blocking.BlockingProcessor;
import com.epam.multithreading.training.task6.classic.ClassicProcessor;
import com.epam.multithreading.training.task6.locks.LockProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

public class ProducerConsumerTest {

    private static Logger logger = LoggerFactory.getLogger(ProducerConsumerTest.class);

    @Test
    public void testGetAllAccounts() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            Processor classicProcessor = new ClassicProcessor(Duration.of(5, ChronoUnit.SECONDS));
            Processor blockingProcessor = new BlockingProcessor(Duration.of(5, ChronoUnit.SECONDS));
            Processor lockProcessor = new LockProcessor(Duration.of(5, ChronoUnit.SECONDS));

            Future classicData = executorService.submit(classicProcessor);
            Future blockingData = executorService.submit(blockingProcessor);
            Future lockData = executorService.submit(lockProcessor);

            logger.info("---- Classic data size: {}", ((List<Integer>)classicData.get()).size());
            logger.info("---- Blocking data size: {}", ((List<Integer>)blockingData.get()).size());
            logger.info("---- Locks data size: {}", ((List<Integer>)lockData.get()).size());

            assertNotNull(classicData.get());
            assertNotNull(blockingData.get());
            assertNotNull(lockData.get());

            executorService.shutdown();
        } catch (Exception e) {
            logger.error("Processor caught error: {}", e.getLocalizedMessage());
        }
    }

}
