package com.epam.multithreading.training.task5.model;

public enum Currency {
    USD(1.0),
    EUR(0.95),
    GBP (0.81),
    CAD(1.29),
    CNY(6.69),
    RUB(55.58);

    private double value;
    private Currency(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
