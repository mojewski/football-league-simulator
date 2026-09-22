package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.List;

public class Match {
    private final Team homeTeam;
    private final Team awayTeam;

    private boolean isPlayed;

    private final List<Player> homeStartingEleven;
    private final List<Player> homeBench;
    private final List<Player> awayStartingEleven;
    private final List<Player> awayBench;

    private MatchStats stats;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.homeStartingEleven = homeTeam.getActiveLineup().getStartingEleven();
        this.homeBench = homeTeam.getActiveLineup().getBench();
        this.awayStartingEleven = awayTeam.getActiveLineup().getStartingEleven();
        this.awayBench = awayTeam.getActiveLineup().getBench();
    }

    public void setPlayed() {
        this.isPlayed = true;
    }

    public void setStats(MatchStats stats) {
        this.stats = stats;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public boolean isPlayed() { return isPlayed; }
    public int getHomeGoals() {
        return stats.getHomeGoals();
    }
    public int getAwayGoals() {
        return stats.getAwayGoals();
    }
    public List<Player> getHomeStartingEleven() {
        return homeStartingEleven;
    }
    public List<Player> getHomeBench() {
        return homeBench;
    }
    public List<Player> getAwayStartingEleven() {
        return awayStartingEleven;
    }
    public List<Player> getAwayBench() {
        return awayBench;
    }
    public MatchStats getMatchStats() {
        return stats;
    }
}
