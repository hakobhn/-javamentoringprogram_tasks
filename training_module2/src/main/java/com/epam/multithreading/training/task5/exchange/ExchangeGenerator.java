package com.epam.multithreading.training.task5.exchange;

import com.epam.multithreading.training.task5.model.AccountDTO;
import com.epam.multithreading.training.task5.services.AccountService;
import com.epam.multithreading.training.task5.services.impl.AccountServiceImpl;
import com.epam.multithreading.training.task5.util.CollectionUtils;

import java.util.List;

public class ExchangeGenerator extends Thread {

    private AccountService accountService = new AccountServiceImpl();

    @Override
    public void run() {
        while (true) {

            List<AccountDTO> accounts = accountService.getAll();

            CollectionUtils.getRandomItemFromList(accounts);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
