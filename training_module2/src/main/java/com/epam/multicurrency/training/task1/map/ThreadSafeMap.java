package com.epam.multicurrency.training.task1.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ThreadSafeMap<K, V> extends HashMap<K, V> {

    private final HashMap<K, V> container = new HashMap<>();
    private final Lock lock = new ReentrantLock();

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return container.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        try {
            lock.lock();
            return container.putIfAbsent(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        try {
            lock.lock();
            return container.computeIfAbsent(key, mappingFunction);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        try {
            return container.get(key);
        } finally {
            lock.unlock();
        }
    }

    //should return count of all value objects in the map
    @Override
    public int size() {
        try {
            lock.lock();
            return container.size();
        } finally {
            lock.unlock();
        }
    }

    //should return value object on success removing it by the key
    @Override
    public V remove(Object key){
        V val;
        try {
            lock.lock();
            val = container.get(key);
            container.remove(key);
        } finally {
            lock.unlock();
        }
        return val;
    }

    @Override
    public boolean isEmpty() {
        try {
            lock.lock();
            return container.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        try {
            lock.lock();
            return container.keySet().stream().collect(Collectors.toUnmodifiableSet());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Collection<V> values() {
        try {
            lock.lock();
            return container.values().stream().collect(Collectors.toUnmodifiableList());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        try {
            lock.lock();
            return container.toString();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThreadSafeMap<?, ?> that = (ThreadSafeMap<?, ?>) o;
        try {
            lock.lock();
            return Objects.equals(container, that.container);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int hashCode() {
        try {
            lock.lock();
            return Objects.hash(super.hashCode(), container);
        } finally {
            lock.unlock();
        }
    }
}
