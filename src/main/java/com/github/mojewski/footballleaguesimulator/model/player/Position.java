package com.github.mojewski.footballleaguesimulator.model.player;

public enum Position {
    FORWARD(0.35, 0.10, 0.05, 0.20, 0.20, 0.10),
    MIDFIELDER(0.15, 0.30, 0.10, 0.15, 0.25, 0.05),
    DEFENDER(0.05, 0.15, 0.35, 0.15, 0.05, 0.25),
    GOALKEEPER(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    private final double shootingWeight;
    private final double passingWeight;
    private final double defendingWeight;
    private final double paceWeight;
    private final double dribblingWeight;
    private final double physicalWeight;

    Position(double shootingWeight, double passingWeight, double defendingWeight,
             double paceWeight, double dribblingWeight, double physicalWeight) {
        this.shootingWeight = shootingWeight;
        this.passingWeight = passingWeight;
        this.defendingWeight = defendingWeight;
        this.paceWeight = paceWeight;
        this.dribblingWeight = dribblingWeight;
        this.physicalWeight = physicalWeight;
    }

    public int calculateOverall(int shooting, int passing, int defending, int pace, int dribbling, int physical) {
        double weightedRating = (shooting * shootingWeight)
                + (passing * passingWeight)
                + (defending * defendingWeight)
                + (pace * paceWeight)
                + (dribbling * dribblingWeight)
                + (physical * physicalWeight);
        return (int) Math.round(weightedRating);
    }

    public double getShootingBoost() {
        return switch (this) {
            case FORWARD -> 1.2;
            case MIDFIELDER -> 0.9;
            case DEFENDER -> 0.4;
            case GOALKEEPER -> 0.0;
        };
    }

    public double getPassingBoost() {
        return switch (this) {
            case FORWARD -> 0.9;
            case MIDFIELDER -> 1.2;
            case DEFENDER -> 0.8;
            case GOALKEEPER -> 0.0;
        };
    }

    public double getDefendingBoost() {
        return switch (this) {
            case FORWARD -> 0.5;
            case MIDFIELDER -> 0.9;
            case DEFENDER -> 1.25;
            case GOALKEEPER -> 0.0;
        };
    }

    public double getShootingWeight() { return shootingWeight; }
    public double getPassingWeight() { return passingWeight; }
    public double getDefendingWeight() { return defendingWeight; }
    public double getPaceWeight() { return paceWeight; }
    public double getDribblingWeight() { return dribblingWeight; }
    public double getPhysicalWeight() { return physicalWeight; }
}