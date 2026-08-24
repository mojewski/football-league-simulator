package com.github.mojewski.footballleaguesimulator.model.player;

public class PlayerContract {

    private int salaryPerYear;
    private int durationInYears;

    public PlayerContract(int salaryPerYear, int durationInYears) {
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

    public int getSalaryPerYear() { return salaryPerYear; }
    public int getDurationInYears() { return durationInYears; }
}
