package com.github.mojewski.footballleaguesimulator.service.match.event;

import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.List;
import java.util.function.ToDoubleFunction;

public class WeightedRandomSelector {

    public static <T> T selectWeighted(List<T> items, ToDoubleFunction<T> weightFunction, RandomNumberGenerator random) {
        if (items.isEmpty()) return null;

        double totalWeight = items.stream().mapToDouble(weightFunction).sum();
        if (totalWeight <= 0) return items.getLast();

        double randomValue = random.getRandomDouble(0.0, totalWeight);
        double currentSum = 0.0;

        for (T item : items) {
            currentSum += weightFunction.applyAsDouble(item);
            if (randomValue <= currentSum) {
                return item;
            }
        }
        return items.getLast();
    }
}