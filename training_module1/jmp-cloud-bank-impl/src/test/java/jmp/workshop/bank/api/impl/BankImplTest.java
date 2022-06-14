package jmp.workshop.bank.api.impl;

import jmp.workshop.dto.BankCardType;
import jmp.workshop.dto.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankImplTest {
    @Test
    public void testCreateBankCard() {
        BankImpl bank = BankImpl.getInstance();
        assertNotNull( bank.createBankCard(
                new User("Test", "Test", LocalDate.now().minusYears(30)),
                BankCardType.CREDIT));
    }

    @Test
    public void testCreateBankCardWithNullType() {
        BankImpl bank = BankImpl.getInstance();
        assertThrows( IllegalArgumentException.class, () -> {
            bank.createBankCard(
                    new User("Test", "Test", LocalDate.now().minusYears(30)),
                    null);
        });
    }
}
