package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

import java.util.Comparator;

public enum LeaderboardCriterion {
    MATCHES(Comparator.comparingInt((Player p) -> p.getStats().getMatchesPlayed()).reversed()),
    MINUTES(Comparator.comparingInt((Player p) -> p.getStats().getMinutesPlayed())
            .thenComparingInt(p -> -p.getStats().getMatchesPlayed())
            .reversed()),
    MINUTES_PER_MATCH(Comparator.comparingDouble((Player p) -> p.getStats().getMinutesPerMatch()).reversed()),
    GOALS(Comparator.comparingInt((Player p) -> p.getStats().getGoals()).
            thenComparingInt(p -> p.getStats().getAssists())
            .thenComparingInt(p -> -p.getStats().getMatchesPlayed())
            .reversed()),
    ASSISTS(Comparator.comparingInt((Player p) -> p.getStats().getAssists())
            .thenComparingInt(p -> p.getStats().getGoals())
            .thenComparingInt(p -> -p.getStats().getMatchesPlayed())
            .reversed()),
    CLEAN_SHEETS(Comparator.comparingInt((Player p) -> p.getStats().getCleanSheets()).reversed()),
    YELLOW_CARDS(Comparator.comparingInt((Player p) -> p.getStats().getYellowCards()).reversed()),
    RED_CARDS(Comparator.comparingInt((Player p) -> p.getStats().getRedCards())
            .thenComparingInt(p -> p.getStats().getYellowCards()).reversed()),
    AVERAGE_RATING(Comparator.comparingDouble((Player p) -> p.getStats().getAverageRating()).reversed()),
    FORM(Comparator.comparingDouble((Player p) -> p.getStats().getForm()).reversed()),
    DAYS_INJURED(Comparator.comparingInt((Player p) -> p.getStats().getDaysInjured()).reversed());

    private final Comparator<Player> comparator;

    LeaderboardCriterion(Comparator<Player> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Player> getComparator() {
        return comparator;
    }
}
