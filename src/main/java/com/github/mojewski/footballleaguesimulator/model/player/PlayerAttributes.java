package com.github.mojewski.footballleaguesimulator.model.player;

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

    public void decreaseSkills(int dropAmount) {
        this.shooting = Math.max(1, this.shooting - dropAmount);
        this.passing = Math.max(1, this.passing - dropAmount);
        this.defending = Math.max(1, this.defending - dropAmount);
    }

    public int getShooting() { return shooting; }
    public int getDefending() { return defending; }
    public int getPassing() { return passing; }
    public int getPotential() { return potential; }
}
