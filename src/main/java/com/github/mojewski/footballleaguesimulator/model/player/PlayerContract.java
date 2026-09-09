package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.service.RandomNumberGenerator;

public class PlayerContract {

    private double salaryPerYear;
    private int durationInYears;

    public PlayerContract(double salaryPerYear, int durationInYears) {
        this.salaryPerYear = salaryPerYear;
        this.durationInYears = durationInYears;
    }

    public boolean isExpired() {
        return durationInYears <= 0;
    }

    public void passSeason() {
        if (durationInYears > 0) {
            this.durationInYears--;
        }
    }

    public double getSalaryPerYear() { return salaryPerYear; }
    public int getDurationInYears() { return durationInYears; }
}
