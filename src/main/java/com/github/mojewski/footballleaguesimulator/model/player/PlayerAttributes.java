package com.github.mojewski.footballleaguesimulator.model.player;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerAttributes {

    private int shooting;
    private int defending;
    private int passing;
    private int potential;

    public PlayerAttributes(int shooting, int defending, int passing, int potential) {
        this.shooting = shooting;
        this.defending = defending;
        this.passing = passing;
        this.potential = potential;
    }

    public int calculateOverall(Position position) {
        return position.calculateOverall(this.shooting, this.passing, this.defending);
    }

    public void decreaseSkills(int dropAmount) {
        this.shooting = Math.max(1, this.shooting - dropAmount);
        this.passing = Math.max(1, this.passing - dropAmount);
        this.defending = Math.max(1, this.defending - dropAmount);
    }

    public void increaseSkills(int seasonForm, Position position) {
        int overall = calculateOverall(position);

        double shootingBoost = position.getShootingBoost();
        double passingBoost = position.getPassingBoost();
        double defendingBoost = position.getDefendingBoost();

        if(overall < potential) {
            int variation = 0;
            if(seasonForm <= 7) {
                variation = ThreadLocalRandom.current().nextInt(1, 3);
            } else if (seasonForm <= 8) {
                variation = ThreadLocalRandom.current().nextInt(4, 7);
            } else if(seasonForm <= 9) {
                variation = ThreadLocalRandom.current().nextInt(8, 11);
            } else if(seasonForm <= 10) {
                variation = ThreadLocalRandom.current().nextInt(12, 15);
            }

            int totalPointsToDistribute = (int) Math.round(variation * shootingBoost)
                    + (int) Math.round(variation * passingBoost)
                    + (int) Math.round(variation * defendingBoost);

            for (int i = 0; i < totalPointsToDistribute; i++) {
                if (calculateOverall(position) >= potential) {
                    break;
                }

                double draw = ThreadLocalRandom.current().nextDouble(0, shootingBoost + passingBoost + defendingBoost);

                if (draw < shootingBoost) {
                    if (this.shooting < 99) this.shooting++;
                } else if (draw < shootingBoost + passingBoost) {
                    if (this.passing < 99) this.passing++;
                } else {
                    if (this.defending < 99) this.defending++;
                }
            }
        }
    }

    public int getShooting() { return shooting; }
    public int getDefending() { return defending; }
    public int getPassing() { return passing; }
    public int getPotential() { return potential; }
}
