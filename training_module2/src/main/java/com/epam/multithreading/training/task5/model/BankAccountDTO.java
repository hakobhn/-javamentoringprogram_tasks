package com.epam.multithreading.training.task5.model;

import java.math.BigDecimal;

public class BankAccountDTO {
    private String uuid;
    private String name;
    private Currency currency;
    private CardType cardType;
    private BigDecimal balance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "uuid='" + uuid + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }
}
