package com.github.mojewski.footballleaguesimulator.model.player;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerAttributes {

    private int shooting;
    private int defending;
    private int passing;
    private int potential;

    public PlayerAttributes(int shooting, int defending, int passing, int potential) {
        this.shooting = Math.min(99, Math.max(1, shooting));
        this.defending = Math.min(99, Math.max(1, defending));
        this.passing = Math.min(99, Math.max(1, passing));
        this.potential = Math.min(99, Math.max(1, potential));
    }

    public int calculateOverall(Position position) {
        return position.calculateOverall(this.shooting, this.passing, this.defending);
    }

    public void decreaseSkills(int dropAmount, Position position) {
        if (dropAmount <= 0) return;

        for (int i = 0; i < dropAmount; i++) {
            if (this.shooting <= 1 && this.passing <= 1 && this.defending <= 1) {
                break;
            }

            double totalWeight = this.shooting + this.passing + this.defending;
            double draw = ThreadLocalRandom.current().nextDouble(0, totalWeight);

            if (draw < this.shooting) {
                if (this.shooting > 1) this.shooting--;
            } else if (draw < this.shooting + this.passing) {
                if (this.passing > 1) this.passing--;
            } else {
                if (this.defending > 1) this.defending--;
            }
        }
    }

    public void increaseSkills(int seasonForm, Position position) {

        if (seasonForm < 6 || calculateOverall(position) >= potential) {
            return;
        }

        int pointsToDistribute = calculatePointsFromForm(seasonForm);

        double shootingBoost = position.getShootingBoost();
        double passingBoost = position.getPassingBoost();
        double defendingBoost = position.getDefendingBoost();
        double totalWeight = shootingBoost + passingBoost + defendingBoost;

        for (int i = 0; i < pointsToDistribute; i++) {

            if (calculateOverall(position) >= potential) {
                break;
            }

            double draw = ThreadLocalRandom.current().nextDouble(0, totalWeight);

            if (draw < shootingBoost) {
                if (this.shooting < 99) this.shooting++;
            } else if (draw < shootingBoost + passingBoost) {
                if (this.passing < 99) this.passing++;
            } else {
                if (this.defending < 99) this.defending++;
            }
        }
    }

    private int calculatePointsFromForm(int seasonForm) {
        if (seasonForm <= 7) return ThreadLocalRandom.current().nextInt(1, 3);
        if (seasonForm <= 8) return ThreadLocalRandom.current().nextInt(3, 6);
        if (seasonForm <= 9) return ThreadLocalRandom.current().nextInt(6, 9);
        else return ThreadLocalRandom.current().nextInt(9, 13);
    }

    public int getShooting() { return shooting; }
    public int getDefending() { return defending; }
    public int getPassing() { return passing; }
    public int getPotential() { return potential; }
}
