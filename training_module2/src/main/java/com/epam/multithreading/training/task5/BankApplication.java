package com.epam.multithreading.training.task5;

import com.epam.multithreading.training.task5.exchange.ExchangeProcessor;
import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import com.epam.multithreading.training.task5.util.RandomizationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BankApplication {

    private static final Logger logger = LoggerFactory.getLogger(BankApplication.class);

    private final Random random = new Random();

    private AccountService accountService = new AccountServiceImpl();

    public static void main(String[] args) {

        BankApplication application = new BankApplication();
        application.process();

    }

    public void process() {
        accountService.deleteAll();

        List<AccountDTO> accounts = generateAccounts(10);
        accountService.createAll(accounts);

        logger.info("Accounts: {}", accountService.getAll());

        ExchangeProcessor processor = new ExchangeProcessor();
        processor.start();
        try {
            processor.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public List<AccountDTO> generateAccounts(int count) {

        return IntStream.range(0, count).mapToObj(
                i -> {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setSsn(RandomizationUtils.generateSSN());
                    accountDTO.setFullName(RandomizationUtils.generateFullName());
                    accountDTO.setDob(RandomizationUtils.generateDOB());
                    accountDTO.setBankAccounts(generateBankAccounts());
                    return accountDTO;
                }).collect(Collectors.toList());
    }

    public List<BankAccountDTO> generateBankAccounts() {
        return IntStream.range(0, random.nextInt(3)+2).mapToObj(
                i -> {
                    BankAccountDTO bankAccountDTO = new BankAccountDTO();
                    bankAccountDTO.setName(RandomizationUtils.generateBankName());
                    bankAccountDTO.setCurrency(RandomizationUtils.generateCurrency());
                    bankAccountDTO.setCardType(RandomizationUtils.generateCardType());
                    bankAccountDTO.setBalance(RandomizationUtils.generateBalance());
                    return bankAccountDTO;
                }).collect(Collectors.toList());

    }

}
