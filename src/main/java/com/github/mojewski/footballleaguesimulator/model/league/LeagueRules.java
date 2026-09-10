package com.github.mojewski.footballleaguesimulator.model.league;

public class LeagueRules {

    public enum TieBreaker {
        GOAL_DIFFERENCE,
        HEAD_TO_HEAD
    }

    private final int promotionSpots;
    private final int relegationSpots;
    private final int championSpots;
    private final int europeanSpots;
    private final TieBreaker tieBreaker;

    public LeagueRules(int promotionSpots, int relegationSpots, TieBreaker tieBreaker) {
        this(promotionSpots, relegationSpots, 0, tieBreaker);
    }

    public LeagueRules(int promotionSpots, int relegationSpots, int europeanSpots, TieBreaker tieBreaker) {
        this.promotionSpots = promotionSpots;
        this.relegationSpots = relegationSpots;
        this.championSpots = 1;
        this.europeanSpots = europeanSpots;
        this.tieBreaker = tieBreaker;
    }

    public int getPromotionSpots() {
        return promotionSpots;
    }

    public int getRelegationSpots() {
        return relegationSpots;
    }

    public int getChampionSpots() {
        return championSpots;
    }

    public int getEuropeanSpots() {
        return europeanSpots;
    }

    public TieBreaker getTieBreaker() {
        return tieBreaker;
    }
}