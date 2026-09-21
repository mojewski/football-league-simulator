package com.github.mojewski.footballleaguesimulator.service.match;

public class PoissonCalculator {
    public static double[] calculateMatchProbabilities(double homeXG, double awayXG) {
        double homeWinProb = 0.0;
        double drawProb = 0.0;
        double awayWinProb = 0.0;

        for (int homeGoals = 0; homeGoals <= 6; homeGoals++) {
            for (int awayGoals = 0; awayGoals <= 6; awayGoals++) {

                double scoreProb = poissonProbability(homeGoals, homeXG)
                        * poissonProbability(awayGoals, awayXG);

                if (homeGoals > awayGoals) {
                    homeWinProb += scoreProb;
                } else if (homeGoals == awayGoals) {
                    drawProb += scoreProb;
                } else {
                    awayWinProb += scoreProb;
                }
            }
        }
        return new double[]{homeWinProb, drawProb, awayWinProb};
    }

    private static double poissonProbability(int k, double lambda) {
        return (Math.pow(lambda, k) * Math.exp(-lambda)) / factorial(k);
    }

    private static long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) fact *= i;
        return fact;
    }

    public static int generateGoals(double lambda) {
        double L = Math.exp(-lambda);
        double k = 0;
        double p = 1.0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return (int) (k - 1);
    }
}
