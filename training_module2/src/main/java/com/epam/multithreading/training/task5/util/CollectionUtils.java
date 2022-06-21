package com.epam.multithreading.training.task5.util;

import java.util.List;
import java.util.Random;

public class CollectionUtils {

    private static final Random rand = new Random();

    public static <E> E getRandomItemFromList(List<E> coll) {
        return coll.get(rand.nextInt(coll.size()));
    }

}
