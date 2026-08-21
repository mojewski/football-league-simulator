package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.*;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerGenerator {

    private final NameGenerator nameGenerator;

    public PlayerGenerator(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public Player generateReplacement(Player retiringPlayer) {
        Team team = retiringPlayer.getTeam();
        int academyLevel = team.getAcademyRating();

        int age = ThreadLocalRandom.current().nextInt(16, 21);
        Country country = retiringPlayer.getCountry();
        String firstName = nameGenerator.generateFirstName(country);
        String lastName = nameGenerator.generateLastName(country);
        Position position = retiringPlayer.getPosition();

        int rawPotential = academyLevel + ThreadLocalRandom.current().nextInt(-10, 16);
        int potential = Math.min(99, Math.max(30, rawPotential));

        PlayerAttributes attributes = generateAttributesForPosition(position, potential);
        int injuryChance = ThreadLocalRandom.current().nextInt(5, 60);

        return new PlayerBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAge(age)
                .setCountry(country)
                .setPosition(position)
                .setAttributes(attributes)
                .setInjuryChance(injuryChance)
                .setTeam(team)
                .setIsRetired(false)
                .setCurrentState(new AvailableState())
                .getResult();
    }

    private PlayerAttributes generateAttributesForPosition(Position position, int potential) {
        int baseSkill = (int) (potential * ThreadLocalRandom.current().nextDouble(0.6, 0.76));

        int shooting = 0;
        int passing = 0;
        int defending = 0;

        switch (position) {
            case FORWARD -> {
                shooting = boostSkill(baseSkill, 1.2);
                passing = boostSkill(baseSkill, 0.9);
                defending = boostSkill(baseSkill, 0.5);
            }
            case MIDFIELDER -> {
                shooting = boostSkill(baseSkill, 0.9);
                passing = boostSkill(baseSkill, 1.2);
                defending = boostSkill(baseSkill, 0.9);
            }
            case DEFENDER -> {
                shooting = boostSkill(baseSkill, 0.4);
                passing = boostSkill(baseSkill, 0.8);
                defending = boostSkill(baseSkill, 1.25);
            }
            case GOALKEEPER -> {
                shooting = boostSkill(baseSkill, 0.2);
                passing = boostSkill(baseSkill, 0.5);
                defending = boostSkill(baseSkill, 1.3);
            }
        }

        return new PlayerAttributes(shooting, defending, passing, potential);
    }

    private int boostSkill(int baseSkill, double multiplier) {
        int variation = ThreadLocalRandom.current().nextInt(-5, 6);
        int calculated = (int) (baseSkill * multiplier) + variation;
        return Math.min(99, Math.max(1, calculated));
    }
}