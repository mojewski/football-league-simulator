package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.*;
import com.github.mojewski.footballleaguesimulator.model.player.*;
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

        int basePotential = 5 + (int) (academyLevel * 0.85);
        int variation = ThreadLocalRandom.current().nextInt(-10, 11);
        int rawPotential = basePotential + variation;

        int potential = Math.min(99, Math.max(5, rawPotential));

        PlayerAttributes attributes = generateAttributesForPosition(position, potential);
        int injuryChance = ThreadLocalRandom.current().nextInt(5, 60);

        int stamina = ThreadLocalRandom.current().nextInt(20, 100);

        PlayerContract contract = generatePlayerContract(attributes, position);

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
                .setStats(new PlayerStats())
                .setStamina(stamina)
                .setContract(contract)
                .build();
    }

    private PlayerAttributes generateAttributesForPosition(Position position, int potential) {
        int baseSkill = (int) (potential * ThreadLocalRandom.current().nextDouble(0.6, 0.76));

        int shooting = boostSkill(baseSkill, position.getShootingBoost());
        int passing = boostSkill(baseSkill, position.getPassingBoost());
        int defending = boostSkill(baseSkill, position.getDefendingBoost());
        int pace = boostSkill(baseSkill, 1.0);
        int dribbling = boostSkill(baseSkill, position.getPassingBoost());
        int physical = boostSkill(baseSkill, position.getDefendingBoost());

        return new PlayerAttributes(shooting, defending, passing, pace, dribbling, physical, potential);
    }

    private PlayerContract generatePlayerContract(PlayerAttributes attributes, Position position) {
        int duration = ThreadLocalRandom.current().nextInt(1, 6);

        int overall = attributes.calculateOverall(position);

        double baseSalary = Math.pow(overall, 3.6) * 0.7;

        int potentialBonus = Math.max(0, attributes.getPotential() - overall) * 500;

        double variation = ThreadLocalRandom.current().nextDouble(0.90, 1.10);

        int finalSalary = (int) Math.round((baseSalary + potentialBonus) * variation);

        finalSalary = Math.max(500, finalSalary);

        return new PlayerContract(finalSalary, duration);
    }

    private int boostSkill(int baseSkill, double multiplier) {
        int variation = ThreadLocalRandom.current().nextInt(-5, 6);
        int calculated = (int) (baseSkill * multiplier) + variation;
        return Math.min(99, Math.max(1, calculated));
    }
}