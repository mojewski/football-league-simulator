package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.*;
import com.github.mojewski.footballleaguesimulator.model.state.AvailableState;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerGenerator {

    private final NameGenerator nameGenerator;

    public PlayerGenerator(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }
    
    public Player generateReplacement(Player retiringPlayer) {

        Team team = retiringPlayer.getTeam();
        int academyLevel = team.getAcademyRating();

        int age = ThreadLocalRandom.current().nextInt(16,21);
        Country country = retiringPlayer.getCountry();
        String firstName = nameGenerator.generateFirstName(country);
        String lastName = nameGenerator.generateLastName(country);
        Position position = retiringPlayer.getPosition();
        int rawPotential = academyLevel + ThreadLocalRandom.current().nextInt(-10, 16);
        int potential = Math.min(99, Math.max(30, rawPotential));
        int rating = (int) (potential * ThreadLocalRandom.current().nextDouble(0.6, 0.76));
        int injuryChance = ThreadLocalRandom.current().nextInt(5, 60);

        return new PlayerBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAge(age)
                .setCountry(country)
                .setPosition(position)
                .setPotential(potential)
                .setRating(rating)
                .setInjuryChance(injuryChance)
                .setTeam(team)
                .setIsRetired(false)
                .setCurrentState(new AvailableState())
                .getResult();
    }
}
