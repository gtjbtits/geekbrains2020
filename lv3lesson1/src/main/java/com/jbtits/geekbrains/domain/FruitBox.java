package com.jbtits.geekbrains.domain;

import java.util.*;

public class FruitBox<T extends Fruit> {

    private final List<T> store;

    public FruitBox() {
        store = new ArrayList<>();
    }

    public void put(T fruit) {
        checkFruit(fruit);
        store.add(fruit);
    }

    public void putAll(Collection<T> fruits) {
        fruits.forEach(this::checkFruit);
        store.addAll(fruits);
    }

    public void empty(FruitBox<T> target) {
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException("Target box is null");
        }
        target.putAll(this.store);
        this.store.clear();
    }

    public boolean compare(FruitBox<? extends Fruit> fruitBox) {
        if (Objects.isNull(fruitBox)) {
            throw new IllegalArgumentException("Comparable box can't be null");
        }
        return this.getWeight() == fruitBox.getWeight();
    }

    public float getWeight() {
        return (float) store.stream()
                .mapToDouble(Fruit::getWeight)
                .sum();
    }

    public int getCount() {
        return store.size();
    }

    private void checkFruit(T fruit) {
        if (Objects.isNull(fruit)) {
            throw new IllegalArgumentException("Fruit must be not null");
        }
    }
}
