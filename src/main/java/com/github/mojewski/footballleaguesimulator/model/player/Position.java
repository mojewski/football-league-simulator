package com.github.mojewski.footballleaguesimulator.model.player;

public enum Position {
    FORWARD(0.7, 0.2, 0.1),
    MIDFIELDER(0.3, 0.4, 0.3),
    DEFENDER(0.1, 0.3, 0.6),
    GOALKEEPER(0.0, 0.1, 0.9);

    private final double shootingWeight;
    private final double passingWeight;
    private final double defendingWeight;

    Position(double shootingWeight, double passingWeight, double defendingWeight) {
        this.shootingWeight = shootingWeight;
        this.passingWeight = passingWeight;
        this.defendingWeight = defendingWeight;
    }

    public int calculateOverall(int shooting, int passing, int defending) {
        double weightedRating = (shooting * shootingWeight)
                + (passing * passingWeight)
                + (defending * defendingWeight);
        return (int) Math.round(weightedRating);
    }

    public double getShootingBoost() {
        return switch (this) {
            case FORWARD -> 1.2;
            case MIDFIELDER -> 0.9;
            case DEFENDER -> 0.4;
            case GOALKEEPER -> 0.2;
        };
    }

    public double getPassingBoost() {
        return switch (this) {
            case FORWARD -> 0.9;
            case MIDFIELDER -> 1.2;
            case DEFENDER -> 0.8;
            case GOALKEEPER -> 0.5;
        };
    }

    public double getDefendingBoost() {
        return switch (this) {
            case FORWARD -> 0.5;
            case MIDFIELDER -> 0.9;
            case DEFENDER -> 1.25;
            case GOALKEEPER -> 1.3;
        };
    }

}
