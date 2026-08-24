package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.PlayerState;
import java.util.concurrent.ThreadLocalRandom;

public class Player {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int form;

    private Country country;
    private Position position;

    private PlayerAttributes attributes;
    private PlayerStats stats;

    private boolean isForSale;
    private boolean isRetired;

    private Team team;
    private int injuryChance;
    private PlayerState currentState;

    public Player(PlayerBuilder builder) {
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.injuryChance = builder.getInjuryChance();
        this.country = builder.getCountry();
        this.position = builder.getPosition();
        this.age = builder.getAge();
        this.isRetired = builder.getIsRetired();
        this.team = builder.getTeam();
        this.currentState = builder.getCurrentState();
        this.attributes = builder.getAttributes();
        this.stats = builder.getStats();
    }

    public int getOverallRating() {
        return attributes.calculateOverall(this.position);
    }

    public void setState(PlayerState state) {
        this.currentState = state;
    }

    public boolean decideRetirement() {
        if(isRetired) return true;
        int chance = getRetiredProbability();
        int draw = ThreadLocalRandom.current().nextInt(1, 101);
        if(draw <= chance) this.isRetired = true;
        return this.isRetired;
    }

    private int getRetiredProbability() {
        if (this.age < 32) return 0;
        else if (this.age <= 35) return 10;
        else if (this.age <= 38) return 50;
        else if (this.age <= 40) return 70;
        else return 100;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getInjuryChance() { return injuryChance; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
    public boolean isForSale() { return isForSale; }
    public boolean getIsRetired() { return isRetired; }
    public Team getTeam() { return team; }
    public PlayerState getCurrentState() { return currentState; }
    public PlayerAttributes getAttributes() { return attributes; }
    public PlayerStats getStats() { return stats; }
}