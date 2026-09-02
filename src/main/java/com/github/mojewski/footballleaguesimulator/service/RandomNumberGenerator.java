package com.github.mojewski.footballleaguesimulator.service;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public double getRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}