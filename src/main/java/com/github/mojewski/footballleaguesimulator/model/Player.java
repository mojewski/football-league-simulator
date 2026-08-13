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

    public Player(PlayerBuilder playerBuilder) {
        this.firstName = playerBuilder.getFirstName();
        this.lastName = playerBuilder.getLastName();
        this.potential = playerBuilder.getPotential();
        this.rating = playerBuilder.getRating();
        this.country = playerBuilder.getCountry();
        this.position = playerBuilder.getPosition();
        this.age = playerBuilder.getAge();
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
