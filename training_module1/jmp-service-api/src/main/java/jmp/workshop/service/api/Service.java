package jmp.workshop.service.api;

import jmp.workshop.dto.BankCard;
import jmp.workshop.dto.Subscription;
import jmp.workshop.dto.User;
import jmp.workshop.service.api.exceptions.NotFoundException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard card);
    Optional<Subscription> getSubscriptionByBankCardNumber(String number);

    default Subscription findSubscriptionByBankCardNumber(String number) {
        return getSubscriptionByBankCardNumber(number)
                .orElseThrow(() -> new NotFoundException("No subscriber found with number: "+number));
    }
    List<User> getAllUsers();

    default double getAverageUsersAge() {
        var now = LocalDate.now();
        return getAllUsers().stream()
                .map(User::getBirthday)
                .mapToLong(dt -> ChronoUnit.YEARS.between(dt, now))
                .average()
                .getAsDouble();

    }

    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) >= 18;
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);

    void cleanAll();


}
