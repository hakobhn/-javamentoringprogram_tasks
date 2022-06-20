package com.epam.multithreading.training.task5.util;

import com.epam.multithreading.training.task5.dao.entity.Account;
import com.epam.multithreading.training.task5.model.AccountDTO;

import java.util.stream.Collectors;

public class AccountConverter {
    public static AccountDTO convertToDto(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        dto.setUuid(entity.getUuid());
        dto.setSsn(entity.getSsn());
        dto.setFullName(entity.getFullName());
        dto.setDob(entity.getDob());
        dto.setBankAccounts(entity.getBankAccounts().stream()
                .map(BankAccountConverter::convertToDto)
                .collect(Collectors.toUnmodifiableList()));
        return dto;
    }

    public static Account convertToEntity(AccountDTO dto) {
        if (dto == null) {
            return null;
        }
        Account entity = new Account();
        entity.setUuid(dto.getUuid());
        entity.setSsn(dto.getSsn());
        entity.setFullName(dto.getFullName());
        entity.setDob(dto.getDob());
        entity.setBankAccounts(dto.getBankAccounts().stream()
                .map(BankAccountConverter::convertToEntity)
                .collect(Collectors.toUnmodifiableList()));
        return entity;
    }
}
