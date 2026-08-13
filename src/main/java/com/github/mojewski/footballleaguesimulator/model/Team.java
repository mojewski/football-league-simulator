package com.github.mojewski.footballleaguesimulator.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private double budget;

    private List<Player> players = new ArrayList<>();

    public Team(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
}
