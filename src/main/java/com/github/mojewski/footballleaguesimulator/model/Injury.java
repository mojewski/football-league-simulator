package com.github.mojewski.footballleaguesimulator.model;

import java.util.concurrent.ThreadLocalRandom;

public record Injury(String name, int minDaysOut, int maxDaysOut ) {

    public Injury {
        if (minDaysOut <= 0 || maxDaysOut < minDaysOut) {
            throw new IllegalArgumentException("Invalid injury duration bounds");
        }
    }
    public int daysOut() {
        return ThreadLocalRandom.current().nextInt(minDaysOut, maxDaysOut + 1);
    }
}
