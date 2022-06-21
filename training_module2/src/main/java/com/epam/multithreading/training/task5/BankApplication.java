package com.epam.multithreading.training.task5;

import com.epam.multithreading.training.task5.exchange.ExchangeRate;
import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import com.epam.multithreading.training.task5.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BankApplication {

    private Logger logger = LoggerFactory.getLogger(BankApplication.class);

    private final Random random = new Random();

    private AccountService accountService = new AccountServiceImpl();

    public static void main(String[] args) {

        BankApplication application = new BankApplication();
        application.process();

    }

    public void process() {
        accountService.deleteAll();

        List<AccountDTO> accounts = generateAccounts(5);
        accountService.createAll(accounts);

        logger.info("Accounts: {}", accountService.getAll());

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.start();
        try {
            exchangeRate.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public List<AccountDTO> generateAccounts(int count) {

        return IntStream.range(0, count).mapToObj(
                i -> {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setSsn(StringUtils.generateSSN());
                    accountDTO.setFullName(StringUtils.generateFullName());
                    accountDTO.setDob(StringUtils.generateDOB());
                    accountDTO.setBankAccounts(generateBankAccounts());
                    return accountDTO;
                }).collect(Collectors.toList());
    }

    public List<BankAccountDTO> generateBankAccounts() {
        return IntStream.range(0, random.nextInt(5)).mapToObj(
                i -> {
                    BankAccountDTO bankAccountDTO = new BankAccountDTO();
                    bankAccountDTO.setName(StringUtils.generateBankName());
                    bankAccountDTO.setCurrency(StringUtils.generateCurrency());
                    bankAccountDTO.setCardType(StringUtils.generateCardType());
                    bankAccountDTO.setBalance(StringUtils.generateBalance());
                    return bankAccountDTO;
                }).collect(Collectors.toList());

    }

}
