package com.github.mojewski.footballleaguesimulator.model.player;

public class PlayerStats {

    private int matchesPlayed;
    private int goals;
    private int assists;
    private int cleanSheets;
    private int yellowCards;
    private int redCards;

    public PlayerStats() {
        this.matchesPlayed = 0;
        this.goals = 0;
        this.assists = 0;
        this.cleanSheets = 0;
        this.yellowCards = 0;
        this.redCards = 0;
    }

    public void addMatchesPlayed() { this.matchesPlayed++; }
    public void addGoal() { this.goals++; }
    public void addAssist() { this.assists++; }
    public void addCleanSheet() { this.cleanSheets++; }
    public void addYellowCard() { this.yellowCards++; }
    public void addRedCard() { this.redCards++; }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getCleanSheets() { return cleanSheets; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
}