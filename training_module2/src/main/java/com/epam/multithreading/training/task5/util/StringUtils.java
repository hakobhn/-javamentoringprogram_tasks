package com.epam.multithreading.training.task5.util;

import com.epam.multithreading.training.task5.model.CardType;
import com.epam.multithreading.training.task5.model.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StringUtils {

    private static final Random rand = new Random();

    private static final List<String> names = Arrays.asList(new String[]{"Adam", "Boris", "Liam",
            "Olivia", "Noah", "Emma", "Oliver", "Charlotte", "Elijah", "Amelia", "James", "Ava", "William", "Sophia",
            "Benjamin", "Isabella", "Lucas", "Mia", "Henry", "Theodore", "Evelyn", "Harper", "Ember", "Apollo"});

    private static final List<String> surnames = Arrays.asList(new String[]{"Smith", "Johnson","Williams","Brown","Jones",
            "Garcia","Miller","Davis","Wilson","Thomas","Taylor","Moore","Martin","Lee","Thompson","White","Clark",
            "Walker","Young","King","Wright","Flores","Nelson","Baker"});

    private static final List<String> banks = Arrays.asList(new String[]{"JPMorgan Chase & Co.", "Bank of America Corp.",
            "Wells Fargo & Co.","Truist Bank","Fifth Third Bank","Goldman Sachs Group Inc.","Morgan Stanley","Citigroup Inc."
            ,"TD Group US Holdings LLC"});

    private static final double MAX_BALANCE = 100000d;

    public static String generateFullName() {
        return new StringBuilder(names.get(rand.nextInt(names.size()))+" "+surnames.get(rand.nextInt(surnames.size()))).toString();
    }

    public static String generateSSN() {
        int number = rand.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static LocalDate generateDOB() {
        return LocalDate.now()
                .minusYears(rand.nextInt(50) + 18)
                .minusMonths(rand.nextInt(12))
                .minusDays(rand.nextInt(31));
    }

    public static String generateBankName() {
        int number = rand.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public static Currency generateCurrency() {
        return Collections.unmodifiableList(Arrays.asList(Currency.values()))
                .get(rand.nextInt(Currency.values().length));
    }

    public static CardType generateCardType() {
        return Collections.unmodifiableList(Arrays.asList(CardType.values()))
                .get(rand.nextInt(CardType.values().length));
    }

    public static BigDecimal generateBalance() {
        return BigDecimal.valueOf(MAX_BALANCE*(rand.nextDouble())).setScale(1, RoundingMode.HALF_UP);
    }

}
