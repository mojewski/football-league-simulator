package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.player.FreeAgents;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.state.NeutralState;
import com.github.mojewski.footballleaguesimulator.model.team.state.TeamMoraleState;
import com.github.mojewski.footballleaguesimulator.service.LineupUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Team {

    private static final int STARTING_ELEVEN_SIZE = 11;

    private Long id;
    private String name;
    private double budget;
    private double startingBudget;
    private int academyRating;
    private int reputation;
    private TeamMoraleState currentState;
    private Formation formation;

    private League league;

    private TeamStats teamStats = new TeamStats();
    private List<Player> players = new ArrayList<>();

    private MatchLineup activeLineup;

    public Team(String name, double budget, int academyRating, int reputation, Formation formation) {
        this.name = name;
        this.budget = budget;
        this.startingBudget = budget;
        this.academyRating = academyRating;
        this.reputation = reputation;
        this.currentState = new NeutralState();
        this.formation = formation;
    }

    public void setFormation(Formation formation) { this.formation = formation; }

    public void setTeamState(TeamMoraleState state) {
        this.currentState = state;
    }

    public void setLeague(League league) { this.league = league; }

    public void updateMorale(MatchResult result) {
        this.currentState.onMatchEnd(this, result);
    }

    public double calculateEffectiveTeamRating() {
        double baseRating = calculateTeamRating();
        return baseRating * currentState.getMoraleModifier();
    }

    public void addPlayer(Player player) {
        if (player != null && !players.contains(player)) {
            players.add(player);
            player.setTeam(this);
        }
    }

    public void removePlayer(Player player) {
        if (player != null && players.contains(player)) {
            players.remove(player);
            player.setTeam(null);
        }
    }

    public void releasePlayerToFreeAgents(Player player, FreeAgents freeAgents) {
        if (player != null && players.contains(player)) {
            removePlayer(player);
            player.terminateContract();
            freeAgents.addFreeAgent(player);
        }
    }

    public void addBudget(double amount) { this.budget += amount; }
    public void subtractBudget(double amount) { this.budget -= amount; }

    private List<Player> getBestLineup() {
        return LineupUtils.buildLineupForFormation(
                players,
                formation,
                Comparator.comparingInt(Player::getOverall).reversed()
        );
    }

    public double calculateTeamRating() {
        return getBestLineup().stream()
                .mapToInt(Player::getEffectiveOverall)
                .average()
                .orElse(0.0);
    }

    public double calculateTeamAttackRating() {
        return getBestLineup().stream()
                .filter(player -> player.getPosition() == Position.FORWARD)
                .mapToInt(Player::getEffectiveOverall)
                .average()
                .orElse(0.0);
    }

    public double calculateTeamMidfieldRating() {
        return getBestLineup().stream()
                .filter(player -> player.getPosition() == Position.MIDFIELDER)
                .mapToInt(Player::getEffectiveOverall)
                .average()
                .orElse(0.0);
    }

    public double calculateTeamDefenseRating() {
        return getBestLineup().stream()
                .filter(player -> player.getPosition() == Position.DEFENDER || player.getPosition() == Position.GOALKEEPER)
                .mapToInt(Player::getEffectiveOverall)
                .average()
                .orElse(0.0);
    }

    public void setActiveLineup(MatchLineup lineup) { this.activeLineup = lineup; }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
    public double getStartingBudget() { return startingBudget; }
    public int getAcademyRating() { return academyRating; }
    public int getReputation() { return reputation; }
    public League getLeague() { return league; }
    public List<Player> getPlayerList() { return players; }
    public TeamStats getTeamStats() { return teamStats; }
    public TeamMoraleState getCurrentState() { return currentState; }
    public Formation getFormation() { return formation; }
    public MatchLineup getActiveLineup() { return activeLineup; }
}