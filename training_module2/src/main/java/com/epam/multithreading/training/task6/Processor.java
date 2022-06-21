package com.epam.multithreading.training.task6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class Processor implements Callable<List<Integer>> {
    protected List<Integer> data = new ArrayList<>();
    public abstract List<Integer> call() throws InterruptedException;

    public List<Integer> getData() {
        return data;
    }
}
