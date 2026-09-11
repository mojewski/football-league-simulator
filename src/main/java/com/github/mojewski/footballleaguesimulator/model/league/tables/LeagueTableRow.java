package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class LeagueTableRow {

    private final Team team;
    private int position;

    public LeagueTableRow(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamName() {
        return team.getName();
    }

    public int getMatchesPlayed() {
        return team.getTeamStats().getMatchesPlayed();
    }

    public int getWins() {
        return team.getTeamStats().getWins();
    }

    public int getDraws() {
        return team.getTeamStats().getDraws();
    }

    public int getLosses() {
        return team.getTeamStats().getLosses();
    }

    public int getGoalsScored() {
        return team.getTeamStats().getGoals();
    }

    public int getGoalsConceded() {
        return team.getTeamStats().getGoalsConceded();
    }

    public int getGoalDifference() {
        return team.getTeamStats().getGoalDifference();
    }

    public int getPoints() {
        return team.getTeamStats().getPoints();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}