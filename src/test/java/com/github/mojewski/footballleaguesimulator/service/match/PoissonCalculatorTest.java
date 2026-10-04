package com.github.mojewski.footballleaguesimulator.service.match;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoissonCalculatorTest {

    @Test
    void calculateMatchProbabilitiesShouldSumToAlmostOne() {
        double homeXG = 1.6;
        double awayXG = 1.2;

        double[] probabilities = PoissonCalculator.calculateMatchProbabilities(homeXG, awayXG);
        double totalProbability = probabilities[0] + probabilities[1] + probabilities[2];

        assertEquals(1.0, totalProbability, 0.01);
    }

    @Test
    void calculateMatchProbabilitiesWithSymmetricXG() {
        double[] probabilities = PoissonCalculator.calculateMatchProbabilities(1.5, 1.5);

        assertEquals(probabilities[0], probabilities[2], 0.01);
    }

    @Test
    void calculateMatchProbabilitiesWithDominantHomeTeam() {
        double[] probabilities = PoissonCalculator.calculateMatchProbabilities(3.0, 0.5);

        assertTrue(probabilities[0] > probabilities[1]);
        assertTrue(probabilities[0] > probabilities[2]);
    }
}