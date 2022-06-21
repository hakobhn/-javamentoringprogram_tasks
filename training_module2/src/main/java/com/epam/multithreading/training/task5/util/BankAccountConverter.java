package com.epam.multithreading.training.task5.util;

import com.epam.multithreading.training.task5.dao.entity.BankAccount;
import com.epam.multithreading.training.task5.model.BankAccountDTO;

public class BankAccountConverter {
    public static BankAccountDTO convertToDto(BankAccount entity) {
        if (entity == null) {
            return null;
        }
        BankAccountDTO dto = new BankAccountDTO();
        dto.setUuid(entity.getUuid());
        dto.setName(entity.getName());
        dto.setCurrency(entity.getCurrency());
        dto.setCardType(entity.getCardType());
        dto.setBalance(entity.getBalance());

        return dto;
    }

    public static BankAccount convertToEntity(BankAccountDTO dto) {
        if (dto == null) {
            return null;
        }
        BankAccount entity = new BankAccount();
        entity.setUuid(dto.getUuid());
        entity.setName(dto.getName());
        entity.setCurrency(dto.getCurrency());
        entity.setCardType(dto.getCardType());
        entity.setBalance(dto.getBalance());

        return entity;
    }
}
