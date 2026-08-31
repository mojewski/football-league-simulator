package com.github.mojewski.footballleaguesimulator.model.team.team_strategy;

public interface MatchStrategy {

    double getAttackModifier();
    double getMidfieldModifier();
    double getDefenseModifier();
    double getStaminaModifier();
}
