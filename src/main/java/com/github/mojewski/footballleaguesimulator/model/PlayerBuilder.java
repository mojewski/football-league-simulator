package com.github.mojewski.footballleaguesimulator.model;

public class PlayerBuilder {

    private String firstName;
    private String lastName;
    private int potential;
    private int rating;
    private Country country;
    private Position position;
    private int age;

    public PlayerBuilder setFirstName(String firstName) { this.firstName = firstName; return this; }
    public PlayerBuilder setLastName(String lastName) { this.lastName = lastName; return this; }
    public PlayerBuilder setPotential(int potential) { this.potential = potential; return this; }
    public PlayerBuilder setRating(int rating) { this.rating = rating; return this; }
    public PlayerBuilder setCountry(Country country) { this.country = country; return this; }
    public PlayerBuilder setPosition(Position position) { this.position = position; return this; }
    public PlayerBuilder setAge(int age) { this.age = age; return this; }

    public Player getResult() { return new Player(this); }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getPotential() { return potential; }
    public int getRating() { return rating; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
}
