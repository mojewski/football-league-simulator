package com.github.mojewski.footballleaguesimulator.service.match.event;

public enum PositionWeight {

    FORWARD(2.0,1.2,0.8,0.7,1.2),
    MIDFIELDER(1.3,2.0,1.2,1.0,1.0),
    DEFENDER(0.8,0.9,1.8,1.8,0.9),
    GOALKEEPER(0.01,0.05,0.2,0.3,0.5);

    private final double goalMultiplier;
    private final double assistMultiplier;
    private final double yellowCardMultiplier;
    private final double redCardMultiplier;
    private final double injuryMultiplier;

    PositionWeight(double goalMultiplier, double assistMultiplier, double yellowCardMultiplier, double redCardMultiplier, double injuryMultiplier) {
        this.goalMultiplier = goalMultiplier;
        this.assistMultiplier = assistMultiplier;
        this.yellowCardMultiplier = yellowCardMultiplier;
        this.redCardMultiplier = redCardMultiplier;
        this.injuryMultiplier = injuryMultiplier;
    }

    public double getGoalMultiplier() { return goalMultiplier; }
    public double getAssistMultiplier() { return assistMultiplier; }
    public double getYellowCardMultiplier() { return yellowCardMultiplier; }
    public double getRedCardMultiplier() { return redCardMultiplier; }
    public double getInjuryMultiplier() { return injuryMultiplier; }
}