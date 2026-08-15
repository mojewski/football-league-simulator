package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.*;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerGenerator {

    public Player generateReplacement(Player retiringPlayer) {

        Team team = retiringPlayer.getTeam();
        int academyLevel = team.getAcademyRating();

        int age = ThreadLocalRandom.current().nextInt(16,21);
        Country country = retiringPlayer.getCountry();
        Position position = retiringPlayer.getPosition();
        int rawPotential = academyLevel + ThreadLocalRandom.current().nextInt(-10, 16);
        int potential = Math.min(99, Math.max(30, rawPotential));
        int rating = (int) (potential * ThreadLocalRandom.current().nextDouble(0.6, 0.76));

        return new PlayerBuilder()
                .setFirstName("New")  //TODO: NameGenerator!
                .setLastName("Player")
                .setAge(age)
                .setCountry(country)
                .setPosition(position)
                .setPotential(potential)
                .setRating(rating)
                .setTeam(team)
                .setIsRetired(false)
                .getResult();
    }
}
