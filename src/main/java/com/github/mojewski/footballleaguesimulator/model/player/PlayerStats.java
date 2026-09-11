package com.github.mojewski.footballleaguesimulator.model.player;

import java.util.ArrayDeque;
import java.util.Deque;

public class PlayerStats {
    private static final int FORM_MATCH_LIMIT = 5;

    private int matchesPlayed;
    private int minutesPlayed;
    private int goals;
    private int assists;
    private int cleanSheets;
    private int yellowCards;
    private int redCards;
    private double averageRating;
    private double totalRatingSum;
    private int ratedMatchesCount;

    private final Deque<Double> lastMatchRatings = new ArrayDeque<>();

    private int daysInjured;

    public PlayerStats() { reset(); }

    public void addMatchesPlayed() { this.matchesPlayed++; }
    public void addMinutesPlayed(int count) { this.minutesPlayed += count; }
    public void addGoals(int count) { if (count > 0) this.goals += count; }
    public void addAssists(int count) { if (count > 0) this.assists += count; }
    public void addCleanSheet() { this.cleanSheets++; }
    public void addYellowCard() { this.yellowCards++; }
    public void addRedCard() { this.redCards++; }
    public void addDayInjured() { this.daysInjured++; }

    public void recordMatchPerformance(double matchRating) {
        if (matchRating < 1.0 || matchRating > 10.0) {
            return;
        }
        this.ratedMatchesCount++;
        this.totalRatingSum += matchRating;

        double rawAverage = this.totalRatingSum / this.ratedMatchesCount;
        this.averageRating = Math.round(rawAverage * 100.0) / 100.0;

        addMatchRating(matchRating);
    }

    public void addMatchRating(double rating) {
        if (lastMatchRatings.size() >= FORM_MATCH_LIMIT) {
            lastMatchRatings.pollFirst();
        }
        lastMatchRatings.addLast(rating);
    }

    public double calculateForm() {
        if (lastMatchRatings.isEmpty()) {
            return 6.0;
        }

        return lastMatchRatings.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(6.0);
    }

    public double getMinutesPerMatch() {
        if (matchesPlayed == 0) {
            return 0.0;
        }
        return (double) minutesPlayed / matchesPlayed;
    }

    public void reset() {
        this.matchesPlayed = 0;
        this.minutesPlayed = 0;
        this.goals = 0;
        this.assists = 0;
        this.cleanSheets = 0;
        this.yellowCards = 0;
        this.redCards = 0;
        this.averageRating = 0.0;
        this.totalRatingSum = 0.0;
        this.ratedMatchesCount = 0;
        this.daysInjured = 0;
        this.lastMatchRatings.clear();
    }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getMinutesPlayed() { return minutesPlayed; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getCleanSheets() { return cleanSheets; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
    public double getAverageRating() { return averageRating; }
    public int getDaysInjured() { return daysInjured; }
    public double getForm() { return calculateForm(); }
}