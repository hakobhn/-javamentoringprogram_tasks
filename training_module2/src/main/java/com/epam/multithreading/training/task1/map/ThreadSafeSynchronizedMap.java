package com.epam.multithreading.training.task1.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ThreadSafeSynchronizedMap<K, V> extends HashMap<K, V> {

    private final HashMap<K, V> container = new HashMap<>();

    @Override
    public synchronized V put(K key, V value) {
        return container.put(key, value);
    }

    @Override
    public synchronized V putIfAbsent(K key, V value) {
         return container.putIfAbsent(key, value);
    }

    @Override
    public synchronized V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return container.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public synchronized V get(Object key) {
        return container.get(key);
    }

    //should return count of all value objects in the map
    @Override
    public synchronized int size() {
        return container.size();
    }

    //should return value object on success removing it by the key
    @Override
    public synchronized V remove(Object key){
        synchronized (container) {
            V val = container.get(key);
            container.remove(key);
            return val;
        }
    }

    @Override
    public synchronized boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public synchronized Set<K> keySet() {
        return container.keySet().stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public synchronized Collection<V> values() {
        return container.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public synchronized String toString() {
        return container.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThreadSafeSynchronizedMap<?, ?> that = (ThreadSafeSynchronizedMap<?, ?>) o;
        return Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), container);
    }

}
