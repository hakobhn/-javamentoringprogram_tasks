package com.epam.multithreading.training.task5.util;

import com.epam.multithreading.training.task5.dao.entity.Account;
import com.epam.multithreading.training.task5.dao.entity.BankAccount;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSerializer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public static JSONObject serialize(Account account) {
        if (account == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        json.put("uuid", account.getUuid());
        json.put("ssn", account.getSsn());
        json.put("fullName", account.getFullName());
        json.put("dob", account.getDob().format(formatter));
        json.put("bankAccounts", account.getBankAccounts().stream()
                .map(BankAccountSerializer::serialize)
                .collect(Collectors.toUnmodifiableList()));

        return json;
    }

    public static Account deserialize(JSONObject json) {
        if (json == null) {
            return null;
        }
        Account account = new Account();
        account.setUuid(json.getString("uuid"));
        account.setSsn(json.getString("ssn"));
        account.setFullName(json.getString("fullName"));
        account.setDob(LocalDate.parse(json.getString("dob"), formatter));

        List<BankAccount> bankAccounts = new ArrayList<>();
        json.getJSONArray("bankAccounts").forEach(
                item -> {
                    bankAccounts.add(BankAccountSerializer.deserialize((JSONObject) item));
                });
        account.setBankAccounts(bankAccounts);

        return account;
    }

}
