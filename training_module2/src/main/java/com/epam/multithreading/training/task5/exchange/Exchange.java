package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.exception.InsufficientFundsException;
import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Callable;

public class Exchange implements Callable<BigDecimal> {

    private static final Logger logger = LoggerFactory.getLogger(Exchange.class);

    private AccountService accountService = new AccountServiceImpl();

    private Map<Currency, BigDecimal> rates;

    private AccountDTO account;
    private BankAccountDTO source;
    private BankAccountDTO target;
    private BigDecimal amount;

    public Exchange(Map<Currency, BigDecimal> rates, AccountDTO account, BankAccountDTO source, BankAccountDTO target, BigDecimal amount) {
        this.rates = rates;
        this.account = account;
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public BigDecimal call() {

        logger.info("Preparing exchange for account: {}", account.getUuid());

        BigDecimal withdrawRate =  rates.get(source.getCurrency());
        BigDecimal withdrawAmount =  withdrawRate.multiply(amount);

        BigDecimal depositRate =  rates.get(target.getCurrency());
        BigDecimal depositAmount =  depositRate.multiply(amount);

        synchronized (source) {
            synchronized (target) {
                if (source.getBalance().compareTo(withdrawAmount) == -1) {
                    throw new InsufficientFundsException("Source balance: "+source.getBalance()+", withdraw: "+withdrawAmount);
                }
                source.setBalance(source.getBalance().subtract(withdrawAmount));
                target.setBalance(target.getBalance().add(depositAmount));

                logger.info("Updating account: {}", account);
                accountService.update(account);
            }
        }

        return amount;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "amount=" + amount +
                '}';
    }
}
