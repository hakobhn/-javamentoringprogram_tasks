package jmp.workshop.bank.api;

import jmp.workshop.dto.BankCard;
import jmp.workshop.dto.BankCardType;
import jmp.workshop.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType cardType);
}
