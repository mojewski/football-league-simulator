package com.github.mojewski.footballleaguesimulator.model.team;

public class TeamStats {

    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;

    private int goals;
    private int goalsConceded;
    private int assists;
    private int cleanSheets;

    private int yellowCards;
    private int redCards;

    private double averageRating;
    private double totalRatingSum;
    private int points;


    public TeamStats() {
        reset();
    }

    public void addWin() {
        this.wins++;
        this.points += 3;
    }

    public void addDraw() {
        this.draws++;
        this.points += 1;
    }

    public void addLoss() {
        this.losses++;
    }

    public void addMatchesPlayed() { this.matchesPlayed++; }
    public void addGoals(int count) { if (count > 0) this.goals += count; }
    public void addGoalsConceded(int count) { if (count > 0) this.goalsConceded += count; }
    public void addAssists(int count) { if (count > 0) this.assists += count; }
    public void addCleanSheet() { this.cleanSheets++; }
    public void addYellowCard() { this.yellowCards++; }
    public void addRedCard() { this.redCards++; }

    public void addMatchRating(double matchRating) {
        if (matchRating < 1.0 || matchRating > 10.0) {
            return;
        }
        this.totalRatingSum += matchRating;

        double rawAverage = this.totalRatingSum / this.matchesPlayed;
        this.averageRating = Math.round(rawAverage * 100.0) / 100.0;
    }

    public int getGoalDifference() {
        return this.goals - this.goalsConceded;
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
        this.points = 0;
    }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getGoals() { return goals; }
    public int getGoalsConceded() { return goalsConceded; }
    public int getAssists() { return assists; }
    public int getCleanSheets() { return cleanSheets; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
    public double getAverageRating() { return averageRating; }
    public int getPoints() { return points; }
}
