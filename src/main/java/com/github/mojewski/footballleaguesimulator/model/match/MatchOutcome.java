package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.PoissonCalculator;

public class MatchOutcome {
    private static final double HOME_ADVANTAGE = 3.0;

    private Team homeTeam;
    private Team awayTeam;

    private double homeXG;
    private double awayXG;

    private double homeWinProbability;
    private double drawProbability;
    private double awayWinProbability;

    private int homeGoals;
    private int awayGoals;

   public MatchOutcome(Match match) {
       this.homeTeam = match.getHomeTeam();
       this.awayTeam = match.getAwayTeam();

       calculateXG();
       generateMatchProbabilities();
       simulateScore();
   }

   private void calculateXG() {
       double homeRating = homeTeam.calculateEffectiveTeamRating() + HOME_ADVANTAGE;
       double awayRating = awayTeam.calculateEffectiveTeamRating();

       double ratingDiff = homeRating - awayRating;

       this.homeXG = Math.max(0.1, 1.35 + (ratingDiff * 0.04));
       this.awayXG = Math.max(0.1, 1.35 - (ratingDiff * 0.04));
   }

   private void generateMatchProbabilities() {
       double[] probabilities = PoissonCalculator.calculateMatchProbabilities(homeXG, awayXG);

       this.homeWinProbability = probabilities[0];
       this.drawProbability = probabilities[1];
       this.awayWinProbability = probabilities[2];
   }

    private void simulateScore() {
        this.homeGoals = PoissonCalculator.generateGoals(this.homeXG);
        this.awayGoals = PoissonCalculator.generateGoals(this.awayXG);
    }

    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public double getHomeXG() {
        return homeXG;
    }
    public double getAwayXG() {
        return awayXG;
    }
    public double getHomeWinProbability() {
       return homeWinProbability;
   }
    public double getDrawProbability() {
        return drawProbability;
    }
    public double getAwayWinProbability() {
        return awayWinProbability;
    }
    public int getHomeGoals() {
        return homeGoals;
    }
    public int getAwayGoals() {
        return awayGoals;
    }
}