package jmp.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Subscription {
    private String bankcard;
    private LocalDate startDate;
}
