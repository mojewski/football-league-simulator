package com.github.mojewski.footballleaguesimulator.model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStatsTest {

    private PlayerStats stats;

    @BeforeEach
    void setUp() {
        stats = new PlayerStats();
    }

    @Test
    void shouldCalculateAverageRatingCorrectly() {
        stats.recordMatchPerformance(8.0);
        stats.recordMatchPerformance(6.0);

        assertEquals(7.0, stats.getAverageRating());
    }

    @Test
    void shouldIgnoreInvalidRatings() {
        stats.recordMatchPerformance(11.0);
        stats.recordMatchPerformance(0.5);

        assertEquals(0.0, stats.getAverageRating());
    }

    @Test
    void shouldResetAllStats() {
        stats.addMatchesPlayed();
        stats.addGoals(2);
        stats.recordMatchPerformance(9.0);

        stats.reset();

        assertEquals(0, stats.getMatchesPlayed());
        assertEquals(0, stats.getGoals());
        assertEquals(0.0, stats.getAverageRating());
    }
}