package com.epam.multithreading.training.task5.util;

import com.epam.multithreading.training.task5.dao.entity.BankAccount;
import com.epam.multithreading.training.task5.model.Currency;
import org.json.JSONObject;

public class BankAccountSerializer {

    public static JSONObject serialize(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        json.put("uuid", bankAccount.getUuid());
        json.put("currency", bankAccount.getCurrency().name());
        json.put("balance", bankAccount.getBalance());

        return json;
    }

    public static BankAccount deserialize(JSONObject json) {
        if (json == null) {
            return null;
        }
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUuid(json.getString("uuid"));
        bankAccount.setCurrency(Currency.valueOf(json.getString("currency")));
        bankAccount.setBalance(json.getBigDecimal("balance"));

        return bankAccount;
    }

}
