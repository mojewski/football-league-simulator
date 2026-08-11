package com.github.mojewski.footballleaguesimulator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents football league within the simulator.
 * Contains league metadata, tier level, location and participating teams.
 */
public class League {

    private Long id;
    private String name;
    private int tier;
    private Country country;

    private List<Team> teams = new ArrayList<>();

    public League(String name, int tier, Country country) {
        this.name = name;
        this.tier = tier;
        this.country = country;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getTier() { return tier; }
    public Country getCountry() { return country; }
}
