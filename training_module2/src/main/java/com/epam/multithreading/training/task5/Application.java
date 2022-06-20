package com.epam.multithreading.training.task5;

import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        AccountService accountService = new AccountServiceImpl();

        accountService.deleteAll();

        BankAccountDTO visa = new BankAccountDTO();
        visa.setCurrency(Currency.USD);
        visa.setBalance(new BigDecimal(2345.5));

        BankAccountDTO master = new BankAccountDTO();
        master.setCurrency(Currency.EUR);
        master.setBalance(new BigDecimal(1422.0));

        BankAccountDTO express = new BankAccountDTO();
        express.setCurrency(Currency.CAD);
        express.setBalance(new BigDecimal(3107.95));

        AccountDTO adam = new AccountDTO();
        adam.setSsn("778-62-8144");
        adam.setFullName("Adam Smith");
        adam.setDob(LocalDate.now().minus(35, ChronoUnit.YEARS));
        adam.setBankAccounts(List.of(visa, master, express));

        accountService.create(adam);

        BankAccountDTO visa1 = new BankAccountDTO();
        visa1.setCurrency(Currency.USD);
        visa1.setBalance(new BigDecimal(1245.5));

        BankAccountDTO master1 = new BankAccountDTO();
        master1.setCurrency(Currency.EUR);
        master1.setBalance(new BigDecimal(479.0));

        AccountDTO john = new AccountDTO();
        john.setSsn("909-03-4642");
        john.setFullName("John Doe");
        john.setDob(LocalDate.now().minus(52, ChronoUnit.YEARS));
        john.setBankAccounts(List.of(visa1, master1));

        accountService.create(john);

        logger.info("All accounts: {}", accountService.getAll());

    }

}
