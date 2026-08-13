package com.github.mojewski.footballleaguesimulator.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private double budget;
    private int academyRating;

    private List<Player> players = new ArrayList<>();

    public Team(String name, double budget, int academyRating) {
        this.name = name;
        this.budget = budget;
        this.academyRating = academyRating;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
    public int getAcademyRating() { return academyRating; }
}
