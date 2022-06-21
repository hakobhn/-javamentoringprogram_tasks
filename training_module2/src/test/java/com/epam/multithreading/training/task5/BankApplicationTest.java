package com.epam.multithreading.training.task5;

import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.model.BankAccountDTO;
import com.epam.multithreading.training.task5.model.CardType;
import com.epam.multithreading.training.task5.model.Currency;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankApplicationTest {

    private static Logger logger = LoggerFactory.getLogger(BankApplicationTest.class);

    private AccountService accountService = new AccountServiceImpl();

    @BeforeEach
    public void setup() {

        accountService.deleteAll();

        BankAccountDTO visa = new BankAccountDTO();
        visa.setName("JPMorgan Chase & Co.");
        visa.setCurrency(Currency.USD);
        visa.setCardType(CardType.Visa);
        visa.setBalance(new BigDecimal(2345.5));

        BankAccountDTO master = new BankAccountDTO();
        master.setName("JPMorgan Chase & Co.");
        master.setCurrency(Currency.EUR);
        master.setCardType(CardType.MasterCard);
        master.setBalance(new BigDecimal(1422.0));

        BankAccountDTO express = new BankAccountDTO();
        express.setName("JPMorgan Chase & Co.");
        express.setCurrency(Currency.CAD);
        express.setCardType(CardType.AmericanExpress);
        express.setBalance(new BigDecimal(3107.95));

        AccountDTO adam = new AccountDTO();
        adam.setSsn("778-62-8144");
        adam.setFullName("Adam Smith");
        adam.setDob(LocalDate.now().minus(35, ChronoUnit.YEARS));
        adam.setBankAccounts(List.of(visa, master, express));

        accountService.create(adam);

        BankAccountDTO visa1 = new BankAccountDTO();
        visa1.setName("JPMorgan Chase & Co.");
        visa1.setCurrency(Currency.USD);
        visa1.setCardType(CardType.Visa);
        visa1.setBalance(new BigDecimal(1245.5));

        BankAccountDTO master1 = new BankAccountDTO();
        master1.setName("JPMorgan Chase & Co.");
        master1.setCurrency(Currency.EUR);
        master1.setCardType(CardType.MasterCard);
        master1.setBalance(new BigDecimal(479.0));

        AccountDTO john = new AccountDTO();
        john.setSsn("909-03-4642");
        john.setFullName("John Doe");
        john.setDob(LocalDate.now().minus(52, ChronoUnit.YEARS));
        john.setBankAccounts(List.of(visa1, master1));

        accountService.create(john);
    }

    @Test
    public void testGetAllAccounts() {
        List<AccountDTO> accounts = accountService.getAll();
        logger.info("All accounts: {}", accounts);
        assertNotNull(accounts);
        assertTrue(accounts.size() == 2);
        assertFalse(accounts.get(0).getBankAccounts().isEmpty());
    }

    @Test
    public void testGetAccountByUUID() {
        List<AccountDTO> accounts = accountService.getAll();
        String uuid = accounts.get(0).getUuid();
        assertNotNull(accountService.get(uuid));
    }

}
