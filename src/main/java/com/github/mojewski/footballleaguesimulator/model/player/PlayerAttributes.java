package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.service.RandomNumberGenerator;

public class PlayerAttributes {

    private int shooting;
    private int defending;
    private int reflex;
    private int handling;
    private int passing;
    private int pace;
    private int dribbling;
    private int physical;
    private int potential;

    public PlayerAttributes(int shooting, int defending, int passing, int pace, int dribbling, int physical, int potential) {
        this.shooting = clamp(shooting);
        this.defending = clamp(defending);
        this.passing = clamp(passing);
        this.pace = clamp(pace);
        this.dribbling = clamp(dribbling);
        this.physical = clamp(physical);
        this.potential = clamp(potential);
        this.reflex = 1;
        this.handling = 1;
    }

    public PlayerAttributes(int reflex, int handling, int passing, int pace, int physical, int potential) {
        this.reflex = clamp(reflex);
        this.handling = clamp(handling);
        this.passing = clamp(passing);
        this.pace = clamp(pace);
        this.physical = clamp(physical);
        this.potential = clamp(potential);
        this.shooting = 1;
        this.defending = 1;
        this.dribbling = 1;
    }

    private int clamp(int value) {
        return Math.min(99, Math.max(1, value));
    }

    public int calculateOverall(Position position) {
        if (position == Position.GOALKEEPER) {
            double weightedRating = (reflex * 0.35)
                    + (handling * 0.30)
                    + (physical * 0.15)
                    + (passing * 0.10)
                    + (pace * 0.10);
            return (int) Math.round(weightedRating);
        }
        return position.calculateOverall(this.shooting, this.passing, this.defending);
    }

    public void decreaseSkills(int dropAmount, Position position, RandomNumberGenerator random) {
        if (dropAmount <= 0) return;

        for (int i = 0; i < dropAmount; i++) {
            if (areAllSkillsAtMinimum(position)) break;

            boolean decreased = false;
            while (!decreased) {
                if (position == Position.GOALKEEPER) {
                    decreased = decreaseGoalkeeperSkill(random);
                } else {
                    decreased = decreaseFieldPlayerSkill(random);
                }
            }
        }
    }

    private boolean decreaseFieldPlayerSkill(RandomNumberGenerator random) {
        double totalWeight = shooting + passing + defending + pace + dribbling + physical;
        double draw = random.getRandomDouble(0, totalWeight);

        if (draw < shooting && shooting > 1) { shooting--; return true; }
        draw -= shooting;
        if (draw < passing && passing > 1) { passing--; return true; }
        draw -= passing;
        if (draw < defending && defending > 1) { defending--; return true; }
        draw -= defending;
        if (draw < pace && pace > 1) { pace--; return true; }
        draw -= pace;
        if (draw < dribbling && dribbling > 1) { dribbling--; return true; }
        draw -= dribbling;
        if (physical > 1) { physical--; return true; }

        return false;
    }

    private boolean decreaseGoalkeeperSkill(RandomNumberGenerator random) {
        double totalWeight = reflex + handling + passing + pace + physical;
        double draw = random.getRandomDouble(0, totalWeight);

        if (draw < reflex && reflex > 1) { reflex--; return true; }
        draw -= reflex;
        if (draw < handling && handling > 1) { handling--; return true; }
        draw -= handling;
        if (draw < passing && passing > 1) { passing--; return true; }
        draw -= passing;
        if (draw < pace && pace > 1) { pace--; return true; }
        draw -= pace;
        if (physical > 1) { physical--; return true; }

        return false;
    }

    public void increaseSkills(int seasonForm, Position position, RandomNumberGenerator random) {
        if (seasonForm < 6 || calculateOverall(position) >= potential) return;

        int pointsToDistribute = calculatePointsFromForm(seasonForm, random);

        for (int i = 0; i < pointsToDistribute; i++) {
            if (calculateOverall(position) >= potential) break;

            if (position == Position.GOALKEEPER) {
                increaseGoalkeeperSkill(random);
            } else {
                increaseFieldPlayerSkill(position, random);
            }
        }
    }

    private void increaseFieldPlayerSkill(Position position, RandomNumberGenerator random) {
        double shootingBoost = position.getShootingBoost();
        double passingBoost = position.getPassingBoost();
        double defendingBoost = position.getDefendingBoost();
        double paceBoost = 1.0;
        double dribblingBoost = position.getPassingBoost();
        double physicalBoost = position.getDefendingBoost();

        double totalWeight = shootingBoost + passingBoost + defendingBoost + paceBoost + dribblingBoost + physicalBoost;
        double draw = random.getRandomDouble(0, totalWeight);

        if (draw < shootingBoost) { if (shooting < 99) shooting++; }
        else if (draw < shootingBoost + passingBoost) { if (passing < 99) passing++; }
        else if (draw < shootingBoost + passingBoost + defendingBoost) { if (defending < 99) defending++; }
        else if (draw < shootingBoost + passingBoost + defendingBoost + paceBoost) { if (pace < 99) pace++; }
        else if (draw < shootingBoost + passingBoost + defendingBoost + paceBoost + dribblingBoost) { if (dribbling < 99) dribbling++; }
        else { if (physical < 99) physical++; }
    }

    private void increaseGoalkeeperSkill(RandomNumberGenerator random) {
        double reflexBoost = 1.3;
        double handlingBoost = 1.2;
        double physicalBoost = 0.9;
        double paceBoost = 0.6;
        double passingBoost = 0.6;

        double totalWeight = reflexBoost + handlingBoost + physicalBoost + paceBoost + passingBoost;
        double draw = random.getRandomDouble(0, totalWeight);

        if (draw < reflexBoost) { if (reflex < 99) reflex++; }
        else if (draw < reflexBoost + handlingBoost) { if (handling < 99) handling++; }
        else if (draw < reflexBoost + handlingBoost + physicalBoost) { if (physical < 99) physical++; }
        else if (draw < reflexBoost + handlingBoost + physicalBoost + paceBoost) { if (pace < 99) pace++; }
        else { if (passing < 99) passing++; }
    }

    private boolean areAllSkillsAtMinimum(Position position) {
        if (position == Position.GOALKEEPER) {
            return reflex <= 1 && handling <= 1 && passing <= 1 && pace <= 1 && physical <= 1;
        }
        return shooting <= 1 && passing <= 1 && defending <= 1 && pace <= 1 && dribbling <= 1 && physical <= 1;
    }

    private int calculatePointsFromForm(int seasonForm, RandomNumberGenerator random) {
        if (seasonForm <= 7) return random.getRandomInt(1, 2);
        if (seasonForm <= 8) return random.getRandomInt(3, 5);
        if (seasonForm <= 9) return random.getRandomInt(6, 8);
        return random.getRandomInt(9, 12);
    }

    public double getStaminaModifier(int stamina) {
        if (stamina >= 70) return 1.0;
        return 0.60 + (stamina / 70.0) * 0.40;
    }

    public int calculateEffectiveOverall(Position position, int stamina) {
        int baseOverall = calculateOverall(position);
        return (int) Math.round(baseOverall * getStaminaModifier(stamina));
    }

    public int getShooting() { return shooting; }
    public int getDefending() { return defending; }
    public int getReflex() { return reflex; }
    public int getHandling() { return handling; }
    public int getPassing() { return passing; }
    public int getPace() { return pace; }
    public int getDribbling() { return dribbling; }
    public int getPhysical() { return physical; }
    public int getPotential() { return potential; }
}