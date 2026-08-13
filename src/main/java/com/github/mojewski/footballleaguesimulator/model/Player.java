package com.github.mojewski.footballleaguesimulator.model;

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

    public Player(String firstName, String lastName, int potential,
                  int rating, Country country, Position position, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.potential = potential;
        this.rating = rating;
        this.country = country;
        this.position = position;
        this.age = age;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getPotential() { return potential; }
    public int getRating() { return rating; }
    public Country getCountry() { return country; }
    public Position getPosition() { return position; }
    public int getAge() { return age; }
}
