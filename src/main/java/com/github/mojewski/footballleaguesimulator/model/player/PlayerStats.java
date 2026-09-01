package com.github.mojewski.footballleaguesimulator.model.player;

public class PlayerStats {

    private int matchesPlayed;
    private int goals;
    private int assists;
    private int cleanSheets;
    private int yellowCards;
    private int redCards;
    private double averageRating;
    private double totalRatingSum;

    private int daysInjuried;

    public PlayerStats() { reset(); }

    public void addMatchesPlayed() { this.matchesPlayed++; }
    public void addGoals(int count) { if (count > 0) this.goals += count; }
    public void addAssists(int count) { if (count > 0) this.assists += count; }
    public void addCleanSheet() { this.cleanSheets++; }
    public void addYellowCard() { this.yellowCards++; }
    public void addRedCard() { this.redCards++; }
    public void addDayInjuried() { this.daysInjuried++; }

    public void calculateAverageRating(double matchRating) {
        if (this.matchesPlayed == 0 || matchRating < 1.0 || matchRating > 10.0) {
            return;
        }
        this.totalRatingSum += matchRating;

        double rawAverage = this.totalRatingSum / this.matchesPlayed;
        this.averageRating = Math.round(rawAverage * 100.0) / 100.0;
    }

    public void reset() {
        this.matchesPlayed = 0;
        this.goals = 0;
        this.assists = 0;
        this.cleanSheets = 0;
        this.yellowCards = 0;
        this.redCards = 0;
        this.averageRating = 0.0;
        this.totalRatingSum = 0.0;
        this.daysInjuried = 0;
    }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getCleanSheets() { return cleanSheets; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
    public double getAverageRating() { return averageRating; }
    public int getDaysInjuried() { return daysInjuried; }
}