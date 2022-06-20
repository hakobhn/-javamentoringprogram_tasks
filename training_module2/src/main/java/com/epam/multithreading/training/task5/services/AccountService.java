package com.epam.multithreading.training.task5.services;

import com.epam.multithreading.training.task5.model.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO create(AccountDTO account);

    AccountDTO get(String uuid);

    void delete(String uuid);

    List<AccountDTO> getAll();

    void deleteAll();
}
