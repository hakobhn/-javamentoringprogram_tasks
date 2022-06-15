package jmp.workshop.dto;

import lombok.Data;

@Data
public abstract class BankCard {
    private String number;
    private User user;

    BankCard(String number, User user) {
        this.number = number;
        this.user = user;
    }
}
