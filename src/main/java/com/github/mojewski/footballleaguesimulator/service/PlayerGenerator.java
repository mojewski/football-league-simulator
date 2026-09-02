package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.*;
import com.github.mojewski.footballleaguesimulator.model.player.state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class PlayerGenerator {

    private final NameGenerator nameGenerator;
    private final RandomNumberGenerator random;

    public PlayerGenerator(NameGenerator nameGenerator, RandomNumberGenerator random) {
        this.nameGenerator = nameGenerator;
        this.random = random;
    }

    public Player generateReplacement(Player retiringPlayer) {
        Team team = retiringPlayer.getTeam();
        int academyLevel = team.getAcademyRating();

        int age = random.getRandomInt(16, 20);
        Country country = retiringPlayer.getCountry();
        String firstName = nameGenerator.generateFirstName(country);
        String lastName = nameGenerator.generateLastName(country);
        Position position = retiringPlayer.getPosition();

        int basePotential = 5 + (int) (academyLevel * 0.85);
        int variation = random.getRandomInt(-10, 10);
        int rawPotential = basePotential + variation;

        int potential = Math.min(99, Math.max(5, rawPotential));

        PlayerAttributes attributes = generateAttributesForPosition(position, potential);
        int injuryChance = random.getRandomInt(5, 60);
        int stamina = random.getRandomInt(20, 99);

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
                .setCurrentState(new AvailableState())
                .setStats(new PlayerStats())
                .setStamina(stamina)
                .setContract(contract)
                .build();
    }

    private PlayerAttributes generateAttributesForPosition(Position position, int potential) {
        int baseSkill = (int) (potential * random.getRandomDouble(0.6, 0.76));

        if (position == Position.GOALKEEPER) {
            int reflex = boostSkill(baseSkill, 1.25);
            int handling = boostSkill(baseSkill, 1.15);
            int passing = boostSkill(baseSkill, 0.8);
            int pace = boostSkill(baseSkill, 0.7);
            int physical = boostSkill(baseSkill, 1.0);

            return new PlayerAttributes(reflex, handling, passing, pace, physical, potential);
        }

        int shooting = boostSkill(baseSkill, position.getShootingBoost());
        int passing = boostSkill(baseSkill, position.getPassingBoost());
        int defending = boostSkill(baseSkill, position.getDefendingBoost());
        int pace = boostSkill(baseSkill, 1.0);
        int dribbling = boostSkill(baseSkill, position.getPassingBoost());
        int physical = boostSkill(baseSkill, position.getDefendingBoost());

        return new PlayerAttributes(shooting, defending, passing, pace, dribbling, physical, potential);
    }

    private PlayerContract generatePlayerContract(PlayerAttributes attributes, Position position) {
        int duration = random.getRandomInt(1, 5);
        int overall = attributes.calculateOverall(position);

        double baseSalary = Math.pow(overall, 3.6) * 0.7;
        int potentialBonus = Math.max(0, attributes.getPotential() - overall) * 500;
        double variation = random.getRandomDouble(0.90, 1.10);

        int finalSalary = (int) Math.round((baseSalary + potentialBonus) * variation);
        finalSalary = Math.max(500, finalSalary);

        return new PlayerContract(finalSalary, duration);
    }

    private int boostSkill(int baseSkill, double multiplier) {
        int variation = random.getRandomInt(-5, 5);
        int calculated = (int) (baseSkill * multiplier) + variation;
        return Math.min(99, Math.max(1, calculated));
    }
}