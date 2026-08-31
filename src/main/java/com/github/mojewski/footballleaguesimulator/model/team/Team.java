package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.team_state.NeutralState;
import com.github.mojewski.footballleaguesimulator.model.team.team_state.TeamMoraleState;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private static final int STARTING_ELEVEN_SIZE = 11;

    private Long id;
    private String name;
    private double budget;
    private int academyRating;
    private int reputation;
    private TeamMoraleState currentState;

    private TeamStats teamStats = new TeamStats();
    private List<Player> players = new ArrayList<>();

    public Team(String name, double budget, int academyRating, int reputation) {
        this.name = name;
        this.budget = budget;
        this.academyRating = academyRating;
        this.reputation = reputation;
        this.currentState = new NeutralState();
    }

    public void setTeamState(TeamMoraleState state) {
        this.currentState = state;
    }

    public void updateMorale(MatchResult result) {
        this.currentState.onMatchEnd(this, result);
    }

    public double calculateEffectiveTeamRating() {
        double baseRating = calculateTeamRating();
        return baseRating * currentState.getMoraleModifier();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addBudget(double amount) { this.budget += amount; }
    public void subtractBudget(double amount) { this.budget -= amount; }

    public List<Player> getStartingEleven() {
        return players.stream()
                .sorted((p1, p2) -> Integer.compare(
                        p2.getAttributes().calculateOverall(p2.getPosition()),
                        p1.getAttributes().calculateOverall(p1.getPosition())
                ))
                .limit(STARTING_ELEVEN_SIZE)
                .toList();
    }

    public double calculateTeamRating() {
        return getStartingEleven().stream()
                .mapToInt(p -> p.getAttributes().calculateOverall(p.getPosition()))
                .average()
                .orElse(0.0);
    }

    public double calculateTeamAttackRating() {
        return getStartingEleven().stream()
                .mapToInt(p -> (p.getAttributes().getShooting()
                        + p.getAttributes().getDribbling()
                        + p.getAttributes().getPace()) / 3)
                .average()
                .orElse(0.0);
    }

    public double calculateTeamMidfieldRating() {
        return getStartingEleven().stream()
                .mapToInt(p -> (p.getAttributes().getPassing()
                        + p.getAttributes().getDribbling()) / 2)
                .average()
                .orElse(0.0);
    }

    public double calculateTeamDefenseRating() {
        return getStartingEleven().stream()
                .mapToInt(p -> (p.getAttributes().getDefending()
                        + p.getAttributes().getPhysical()
                        + p.getAttributes().getPace()) / 3)
                .average()
                .orElse(0.0);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
    public int getAcademyRating() { return academyRating; }
    public int getReputation() { return reputation; }
    public List<Player> getPlayerList() { return players; }
    public TeamStats getTeamStats() { return teamStats; }
    public TeamMoraleState getCurrentState() { return currentState; }
}