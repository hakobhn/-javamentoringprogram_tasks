package com.epam.multithreading.training.task5.dao;

import com.epam.multithreading.training.task5.dao.entity.Account;

import java.util.List;

public interface AccountRepository {

    Account save(Account account);

    Account update(Account account);

    Account findById(String uuid);

    void delete(String uuid);

    List<Account> findAll();

    void deleteAll();

}
