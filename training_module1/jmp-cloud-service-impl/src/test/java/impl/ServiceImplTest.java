package impl;

import jmp.workshop.bank.api.Bank;
import jmp.workshop.bank.api.impl.BankImpl;
import jmp.workshop.dto.BankCard;
import jmp.workshop.dto.BankCardType;
import jmp.workshop.dto.User;
import jmp.workshop.service.api.Service;
import jmp.workshop.service.api.impl.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceImplTest {

    private ServiceImpl service = ServiceImpl.getInstance();
    private Bank bank = BankImpl.getInstance();

    @BeforeEach
    public void setup() {

        service.cleanAll();

        User john = new User("John", "Carter", LocalDate.now().minusYears(40));
        User mike = new User("Mike", "Tyson", LocalDate.now().minusYears(60));
        User adam = new User("Adam", "Smith", LocalDate.now().minusYears(25));

        BankCard johnVisa = bank.createBankCard(john, BankCardType.CREDIT);
        BankCard johnMaster = bank.createBankCard(john, BankCardType.DEBIT);

        service.subscribe(johnVisa);
        service.subscribe(johnMaster);

        BankCard mikeVisa = bank.createBankCard(mike, BankCardType.CREDIT);
        BankCard mikeMaster = bank.createBankCard(mike, BankCardType.CREDIT);
        BankCard mikeExpress = bank.createBankCard(mike, BankCardType.DEBIT);
        BankCard mikeArca = bank.createBankCard(mike, BankCardType.CREDIT);

        service.subscribe(mikeVisa);
        service.subscribe(mikeMaster);
        service.subscribe(mikeExpress);
        service.subscribe(mikeArca);

        BankCard adamArca = bank.createBankCard(adam, BankCardType.DEBIT);

        service.subscribe(adamArca);
    }

    @Test
    public void testServiceInit() {
        ServiceImpl service = ServiceImpl.getInstance();
        assertNotNull( service.getAllUsers());
        System.out.println(service.printUserStorage());
        System.out.println(service.printSubscriptions());
    }

    @Test
    public void testSubscriberFunctionality() {

        System.out.println(service.printUserStorage());
        System.out.println("--------------------------");
        System.out.println(service.printSubscriptions());

        assertEquals(7, service.getSubscriptions().size());
        assertEquals(3, service.getUserStorage().size());

        assertEquals(41, (int)service.getAverageUsersAge());
    }

    @Test
    public void testIsPayableUsery() {
        User john = new User("John", "Carter", LocalDate.now().minusYears(40));
        User greta = new User("Greta", "Thunberg", LocalDate.now().minusYears(15));

        assertTrue(Service.isPayableUser(john));
        assertFalse(Service.isPayableUser(greta));
    }

    @Test
    public void givenNonExistingNumber_whenGetReferenceIsUsed_thenExceptionIsThrown() {
        ServiceImpl service = ServiceImpl.getInstance();
        assertTrue(service.getSubscriptionByBankCardNumber("UNKNOWN").isEmpty());
        assertThrows( Exception.class, () -> {
            service.findSubscriptionByBankCardNumber("UNKNOWN");
        });
    }
}
