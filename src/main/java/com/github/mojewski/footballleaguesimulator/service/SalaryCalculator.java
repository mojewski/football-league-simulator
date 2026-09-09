package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public class SalaryCalculator {

    private static final int MIN_PROFESSIONAL_OVERALL = 15;
    private final RandomNumberGenerator random;

    public SalaryCalculator(RandomNumberGenerator random) {
        this.random = random;
    }

    public double calculateExpectedMonthlySalary(Player player) {
        int overall = player.getOverall();

        if (overall <= MIN_PROFESSIONAL_OVERALL) {
            return 0.0;
        }

        int effectiveOverall = overall - MIN_PROFESSIONAL_OVERALL;
        double baseSalary = Math.pow(effectiveOverall, 2.8) * 0.35;
        double randomFactor = random.getRandomDouble(0.90, 1.10);

        return Math.round((baseSalary * randomFactor) / 10.0) * 10.0;
    }
}