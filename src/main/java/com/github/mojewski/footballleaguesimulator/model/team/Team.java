package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private double budget;
    private int academyRating;
    private int reputation;

    private TeamStats teamStats = new TeamStats();
    private List<Player> players = new ArrayList<>();

    public Team(String name, double budget, int academyRating, int reputation) {
        this.name = name;
        this.budget = budget;
        this.academyRating = academyRating;
        this.reputation = reputation;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addBudget(double amount) { this.budget += amount; }
    public void subtractBudget(double amount) { this.budget -= amount; }

    public double calculateTeamRating() {

        return players.stream()
                .mapToInt(p -> p.getAttributes().calculateOverall(p.getPosition()))
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
}
