package jmp.workshop.bank.api.impl;

import jmp.workshop.bank.api.Bank;
import jmp.workshop.dto.*;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    private static volatile BankImpl service = null;

    private BankImpl() {
    }

    public static BankImpl getInstance() {
        synchronized (BankImpl.class) {
            if (service == null)
                service = new BankImpl();
        }
        return service;
    }

    private BiFunction<User, BankCardType, BankCard> cardGenerator = (us, tp) -> {
        switch (Optional.ofNullable(tp)
                .orElseThrow(()->new IllegalArgumentException("Card type cannot be null!"))) {
            case CREDIT:
               return new CreditBankCard(UUID.randomUUID().toString(), us);
            case DEBIT:
               return new DebitBankCard(UUID.randomUUID().toString(), us);
            default:
                throw new IllegalArgumentException ("Provided invalid card type: "+tp);
        }
    };

    public BankCard createBankCard(User user, BankCardType cardType) {
        return cardGenerator.apply(user, cardType);
    }
}
