package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class Match {
    //TODO: klasa tworzaca MatchDate
    private Team homeTeam;
    private Team awayTeam;

    private boolean isPlayed;
    private int homeGoals;
    private int awayGoals;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }

    public boolean isPlayed() { return isPlayed; }
    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }
}
