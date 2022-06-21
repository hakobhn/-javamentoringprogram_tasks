package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import com.epam.multithreading.training.task5.util.CollectionUtils;
import com.epam.multithreading.training.task5.util.RandomizationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExchangeGenerator extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeGenerator.class);

    private AccountService accountService = new AccountServiceImpl();

    private List<Exchange> exchanges;

    private Map<Currency, BigDecimal> rates;

    public ExchangeGenerator(Map<Currency, BigDecimal> rates, List<Exchange> exchanges) {
        this.rates = rates;
        this.exchanges = exchanges;
    }

    @Override
    public void run() {

        while (true) {
            List<AccountDTO> accounts = accountService.getAll();

            AccountDTO account = CollectionUtils.getRandomItemFromList(accounts);
            logger.info("Generating exchange for account: {}", account);
            BankAccountDTO source = CollectionUtils.getRandomItemFromList(account.getBankAccounts());
            List<BankAccountDTO> bankAccountsCpy = account.getBankAccounts().stream().collect(Collectors.toList());
            bankAccountsCpy.remove(source);
            BankAccountDTO target = CollectionUtils.getRandomItemFromList(bankAccountsCpy);

            synchronized (rates) {
                Exchange exchange = new Exchange(rates, account, source, target, RandomizationUtils.generateExchangeAmount());
                logger.info("Adding exchange: {}", exchange);
                exchanges.add(exchange);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
