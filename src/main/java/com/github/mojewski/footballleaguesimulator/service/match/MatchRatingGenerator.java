package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.match.MatchStats;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.List;

public class MatchRatingGenerator {

    private static final double MIN_RATING = 1.0;
    private static final double MAX_RATING = 10.0;
    private static final double DEFAULT_RATING = 6.0;

    private static final double SAVE_BONUS = 0.5;

    private static final double GK_GOAL_CONCEDED_BONUS = -0.8;
    private static final double DEF_GOAL_CONCEDED_BONUS = -0.7;
    private static final double MID_GOAL_CONCEDED_BONUS = -0.5;
    private static final double ATT_GOAL_CONCEDED_BONUS = -0.3;

    private static final double GK_TEAM_GOAL_BONUS = 0.2;
    private static final double DEF_TEAM_GOAL_BONUS = 0.4;
    private static final double MID_TEAM_GOAL_BONUS = 0.6;
    private static final double ATT_TEAM_GOAL_BONUS = 0.8;

    private static final double ASSIST_BONUS = 0.3;
    private static final double INDIVIDUAL_GOAL_BONUS = 0.5;
    private static final double YELLOW_CARD_BONUS = -0.3;
    private static final double RED_CARD_BONUS = -1.5;

    private final RandomNumberGenerator random;

    public MatchRatingGenerator(RandomNumberGenerator random) {
        this.random = random;
    }

    public double generateHomeGoalkeeperRating(MatchStats stats, Player player) {
        double savesBonus = SAVE_BONUS * stats.getHomeGoalkeeperSaves();
        return calculateRating(stats.getAwayGoals(), stats.getHomeGoals(), GK_GOAL_CONCEDED_BONUS, GK_TEAM_GOAL_BONUS, stats.getEvents(), player) + savesBonus;
    }

    public double generateAwayGoalkeeperRating(MatchStats stats, Player player) {
        double savesBonus = SAVE_BONUS * stats.getAwayGoalkeeperSaves();
        return calculateRating(stats.getHomeGoals(), stats.getAwayGoals(), GK_GOAL_CONCEDED_BONUS, GK_TEAM_GOAL_BONUS, stats.getEvents(), player) + savesBonus;
    }

    public double generateHomeDefenderRating(MatchStats stats, Player player) {
        return calculateRating(stats.getAwayGoals(), stats.getHomeGoals(), DEF_GOAL_CONCEDED_BONUS, DEF_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    public double generateAwayDefenderRating(MatchStats stats, Player player) {
        return calculateRating(stats.getHomeGoals(), stats.getAwayGoals(), DEF_GOAL_CONCEDED_BONUS, DEF_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    public double generateHomeMidfielderRating(MatchStats stats, Player player) {
        return calculateRating(stats.getAwayGoals(), stats.getHomeGoals(), MID_GOAL_CONCEDED_BONUS, MID_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    public double generateAwayMidfielderRating(MatchStats stats, Player player) {
        return calculateRating(stats.getHomeGoals(), stats.getAwayGoals(), MID_GOAL_CONCEDED_BONUS, MID_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    public double generateHomeAttackerRating(MatchStats stats, Player player) {
        return calculateRating(stats.getAwayGoals(), stats.getHomeGoals(), ATT_GOAL_CONCEDED_BONUS, ATT_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    public double generateAwayAttackerRating(MatchStats stats, Player player) {
        return calculateRating(stats.getHomeGoals(), stats.getAwayGoals(), ATT_GOAL_CONCEDED_BONUS, ATT_TEAM_GOAL_BONUS, stats.getEvents(), player);
    }

    private double calculateRating(int goalsConceded, int goalsScored,
                                   double goalConcededWeight, double teamGoalWeight,
                                   List<MatchEvent> events, Player player) {

        double rawRating = DEFAULT_RATING
                + (goalConcededWeight * goalsConceded)
                + (teamGoalWeight * goalsScored)
                + calculateIndividualBonus(events, player)
                + random.getRandomDouble(-1.0, 1.0);

        double clamped = Math.clamp(rawRating, MIN_RATING, MAX_RATING);
        return Math.round(clamped * 10.0) / 10.0;
    }

    private double calculateIndividualBonus(List<MatchEvent> events, Player player) {
        double bonus = 0.0;
        int yellowCards = 0;
        boolean hasRedCard = false;

        for (MatchEvent event : events) {
            if (event.primaryPlayer() != null && event.primaryPlayer().getName().equals(player.getName())) {
                if (event.type() == EventType.GOAL) {
                    bonus += INDIVIDUAL_GOAL_BONUS;
                } else if (event.type() == EventType.YELLOW_CARD) {
                    yellowCards++;
                } else if (event.type() == EventType.RED_CARD) {
                    hasRedCard = true;
                }
            }

            if (event.type() == EventType.GOAL && event.secondaryPlayer() != null && event.secondaryPlayer().getName().equals(player.getName())) {
                bonus += ASSIST_BONUS;
            }
        }

        if (hasRedCard || yellowCards >= 2) {
            bonus += RED_CARD_BONUS;
        } else if (yellowCards == 1) {
            bonus += YELLOW_CARD_BONUS;
        }

        return bonus;
    }
}