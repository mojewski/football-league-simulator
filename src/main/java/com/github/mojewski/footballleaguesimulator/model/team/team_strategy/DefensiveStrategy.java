package com.github.mojewski.footballleaguesimulator.model.team.team_strategy;

public class DefensiveStrategy implements MatchStrategy {

    private static final double ATTACK_MODIFIER = 0.80;
    private static final double MIDFIELD_MODIFIER = 1.0;
    private static final double DEFENSE_MODIFIER = 1.25;
    private static final double STAMINA_DRAIN = 0.90;

    @Override
    public double getAttackModifier() { return ATTACK_MODIFIER; }

    @Override
    public double getDefenseModifier() { return DEFENSE_MODIFIER; }

    @Override
    public double getMidfieldModifier() { return MIDFIELD_MODIFIER; }

    @Override
    public double getStaminaModifier() { return STAMINA_DRAIN; }
}
