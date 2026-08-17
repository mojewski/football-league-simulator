package com.github.mojewski.footballleaguesimulator.model;

import java.util.concurrent.ThreadLocalRandom;

public class Player {

    private Long id;
    private String firstName;
    private String lastName;
    private int potential;
    private int rating;
    private Country country;
    private Position position;
    private int age;
    private boolean isForSale;
    private boolean isRetired;
    private Team team;
    //TODO: injuryChance

    public Player(PlayerBuilder playerBuilder) {
        this.firstName = playerBuilder.getFirstName();
        this.lastName = playerBuilder.getLastName();
        this.potential = playerBuilder.getPotential();
        this.rating = playerBuilder.getRating();
        this.country = playerBuilder.getCountry();
        this.position = playerBuilder.getPosition();
        this.age = playerBuilder.getAge();
        this.isRetired = playerBuilder.getIsRetired();
        this.team = playerBuilder.getTeam();
    }

    private int getRetiredProbability() {
        if (this.age < 32) return 0;
        else if (this.age <= 35) return 10;
        else if (this.age <= 38) return 50;
        else if (this.age <= 40) return 70;
        else return 100;
    }

    public boolean decideRetirement() {
        if(isRetired == true) return true;

        int chance = getRetiredProbability();

        int draw = ThreadLocalRandom.current().nextInt(1,101);

        if(draw <= chance) this.isRetired = true;

        return this.isRetired;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getPotential() { return potential; }
    public int getRating() { return rating; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
    public boolean isForSale() { return isForSale; }
    public boolean getIsRetired() { return isRetired; }
    public Team getTeam() { return team; }
}
