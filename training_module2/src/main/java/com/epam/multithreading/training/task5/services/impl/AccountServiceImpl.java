package com.epam.multithreading.training.task5.services.impl;

import com.epam.multithreading.training.task5.dao.AccountRepository;
import com.epam.multithreading.training.task5.dao.impl.AccountRepositoryImpl;
import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.util.AccountConverter;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository = new AccountRepositoryImpl();


    @Override
    public AccountDTO create(AccountDTO account) {
        return AccountConverter.convertToDto(accountRepository.save(AccountConverter.convertToEntity(account)));
    }

    @Override
    public AccountDTO get(String uuid) {
        return  AccountConverter.convertToDto(accountRepository.findById(uuid));
    }

    @Override
    public void delete(String uuid) {
        accountRepository.delete(uuid);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll().stream()
                .map(AccountConverter::convertToDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
