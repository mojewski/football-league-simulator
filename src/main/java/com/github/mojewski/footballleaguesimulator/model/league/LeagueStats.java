package com.github.mojewski.footballleaguesimulator.model.league;

import com.github.mojewski.footballleaguesimulator.model.match.Match;

public class LeagueStats {

    private int matchesPlayed;
    private int homeWins;
    private int draws;
    private int awayWins;

    private int totalGoals;
    private int totalAssists;
    private int totalCleanSheets;

    private Match highestScoringMatch;
    private Match biggestBlowoutMatch;

    private int totalYellowCards;
    private int totalRedCards;

    private double averageGoalsPerMatch;
    private double homeWinPercentage;
    private double cardsPerMatch;

    public LeagueStats() {
        reset();
    }

    public void registerMatch(Match match) {
        if (match == null || !match.isPlayed()) {
            return;
        }

        this.matchesPlayed++;
        int homeGoals = match.getHomeGoals();
        int awayGoals = match.getAwayGoals();
        int matchGoals = homeGoals + awayGoals;
        this.totalGoals += matchGoals;

        if (homeGoals > awayGoals) {
            this.homeWins++;
        } else if (awayGoals > homeGoals) {
            this.awayWins++;
        } else {
            this.draws++;
        }

        if (this.highestScoringMatch == null || matchGoals > getMatchTotalGoals(this.highestScoringMatch)) {
            this.highestScoringMatch = match;
        }

        int goalDifference = Math.abs(homeGoals - awayGoals);
        if (this.biggestBlowoutMatch == null || goalDifference > getMatchGoalDifference(this.biggestBlowoutMatch)) {
            this.biggestBlowoutMatch = match;
        }

        recalculateMetrics();
    }

    public void addAssists(int count) {
        if (count > 0) {
            this.totalAssists += count;
        }
    }

    public void addCleanSheet() {
        this.totalCleanSheets++;
    }

    public void addCards(int yellowCount, int redCount) {
        this.totalYellowCards += Math.max(0, yellowCount);
        this.totalRedCards += Math.max(0, redCount);
        recalculateMetrics();
    }

    private void recalculateMetrics() {
        if (this.matchesPlayed == 0) {
            this.averageGoalsPerMatch = 0.0;
            this.homeWinPercentage = 0.0;
            this.cardsPerMatch = 0.0;
            return;
        }

        this.averageGoalsPerMatch = round((double) this.totalGoals / this.matchesPlayed);
        this.homeWinPercentage = round(((double) this.homeWins / this.matchesPlayed) * 100.0);
        this.cardsPerMatch = round((double) (this.totalYellowCards + this.totalRedCards) / this.matchesPlayed);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private int getMatchTotalGoals(Match match) {
        return match.getHomeGoals() + match.getAwayGoals();
    }

    private int getMatchGoalDifference(Match match) {
        return Math.abs(match.getHomeGoals() - match.getAwayGoals());
    }

    public void reset() {
        this.matchesPlayed = 0;
        this.homeWins = 0;
        this.draws = 0;
        this.awayWins = 0;
        this.totalGoals = 0;
        this.totalAssists = 0;
        this.totalCleanSheets = 0;
        this.highestScoringMatch = null;
        this.biggestBlowoutMatch = null;
        this.totalYellowCards = 0;
        this.totalRedCards = 0;
        this.averageGoalsPerMatch = 0.0;
        this.homeWinPercentage = 0.0;
        this.cardsPerMatch = 0.0;
    }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getHomeWins() { return homeWins; }
    public int getDraws() { return draws; }
    public int getAwayWins() { return awayWins; }
    public int getTotalGoals() { return totalGoals; }
    public int getTotalAssists() { return totalAssists; }
    public int getTotalCleanSheets() { return totalCleanSheets; }
    public Match getHighestScoringMatch() { return highestScoringMatch; }
    public Match getBiggestBlowoutMatch() { return biggestBlowoutMatch; }
    public int getTotalYellowCards() { return totalYellowCards; }
    public int getTotalRedCards() { return totalRedCards; }
    public double getAverageGoalsPerMatch() { return averageGoalsPerMatch; }
    public double getHomeWinPercentage() { return homeWinPercentage; }
    public double getCardsPerMatch() { return cardsPerMatch; }
}
