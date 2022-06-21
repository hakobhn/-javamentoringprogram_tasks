package com.epam.multithreading.training.task6;

import com.epam.multithreading.training.task6.blocking.BlockingProcessor;
import com.epam.multithreading.training.task6.classic.ClassicProcessor;
import com.epam.multithreading.training.task6.locks.LockProcessor;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessorAnalyzer {

    private final static Logger logger = LoggerFactory.getLogger(BlockingProcessor.class);

    public static void main(String[] args) {
        logger.info("Starting processors...");
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

            executorService.shutdown();
        } catch (Exception e) {
            logger.error("Processor caught error: {}", e.getLocalizedMessage());
        }
    }

}
