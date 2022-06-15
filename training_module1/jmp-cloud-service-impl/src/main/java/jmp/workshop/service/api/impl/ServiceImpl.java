package jmp.workshop.service.api.impl;

import jmp.workshop.dto.BankCard;
import jmp.workshop.dto.Subscription;
import jmp.workshop.dto.User;
import jmp.workshop.service.api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private static volatile ServiceImpl service = null;

    private final Map<User, List<BankCard>> userStorage;
    private final List<Subscription> subscriptions;

    private ServiceImpl() {
        userStorage = new ConcurrentHashMap<>();
        subscriptions = new ArrayList<>();
    }

    public static ServiceImpl getInstance() {
        synchronized (ServiceImpl.class) {
            if (service == null)
                service = new ServiceImpl();
        }
        return service;
    }


    @Override
    public void subscribe(BankCard card) {
        userStorage.computeIfAbsent(card.getUser(), (u) -> new ArrayList<>()).add(card);
        subscriptions.add(new Subscription(card.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String number) {
        return subscriptions.stream()
                .filter(s -> s.getBankcard().equals(number)).findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userStorage.keySet());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return getSubscriptions().stream().filter(predicate).collect(Collectors.toUnmodifiableList());
    }

    public String printUserStorage() {
        return userStorage.entrySet()
                .stream()
                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"\n")
                .collect(Collectors.joining(", "));
    }

    public String printSubscriptions() {
        return subscriptions.toString();
    }

    public Map<User, List<BankCard>> getUserStorage() {
        return userStorage;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void cleanAll() {
        userStorage.clear();
        subscriptions.clear();
    }
}
