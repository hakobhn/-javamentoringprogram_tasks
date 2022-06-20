package com.epam.multithreading.training.task5.dao.entity;

import com.epam.multithreading.training.task5.model.Currency;

import java.math.BigDecimal;

public class BankAccount {
    private String uuid;
    private Currency currency;
    private BigDecimal balance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
