package com.github.mojewski.footballleaguesimulator.model;

import com.github.mojewski.footballleaguesimulator.model.state.PlayerState;

public class PlayerBuilder {

    private String firstName;
    private String lastName;
    private int potential;
    private int rating;
    private int injuryChance;
    private Country country;
    private Position position;
    private int age;
    private boolean isRetired;
    private Team team;

    private PlayerState currentState;

    public PlayerBuilder setFirstName(String firstName) { this.firstName = firstName; return this; }
    public PlayerBuilder setLastName(String lastName) { this.lastName = lastName; return this; }
    public PlayerBuilder setPotential(int potential) { this.potential = potential; return this; }
    public PlayerBuilder setRating(int rating) { this.rating = rating; return this; }
    public PlayerBuilder setInjuryChance(int injuryChance) { this.injuryChance = injuryChance; return this; }
    public PlayerBuilder setCountry(Country country) { this.country = country; return this; }
    public PlayerBuilder setPosition(Position position) { this.position = position; return this; }
    public PlayerBuilder setAge(int age) { this.age = age; return this; }
    public PlayerBuilder setIsRetired(boolean isRetired) { this.isRetired = isRetired; return this; }
    public PlayerBuilder setTeam(Team team) { this.team = team; return this; }
    public PlayerBuilder setCurrentState(PlayerState currentState) { this.currentState = currentState; return this;}

    public Player getResult() { return new Player(this); }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getPotential() { return potential; }
    public int getRating() { return rating; }
    public int getInjuryChance() { return injuryChance; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
    public boolean getIsRetired() { return isRetired; }
    public Team getTeam() { return team; }
    public PlayerState getCurrentState() { return currentState; }
}
