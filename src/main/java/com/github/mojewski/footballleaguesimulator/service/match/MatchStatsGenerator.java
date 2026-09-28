package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.match.MatchStats;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

public class MatchStatsGenerator {
    private final RandomNumberGenerator random;

    public MatchStatsGenerator(RandomNumberGenerator random) {
        this.random = random;
    }

    public void generateMatchStats(MatchStats stats) {
        int homePossession = random.getRandomInt(35, 65);
        stats.setHomePossession(homePossession);
        stats.setAwayPossession(100 - homePossession);

        int totalMatchPasses = random.getRandomInt(800, 1100);
        int homePasses = (totalMatchPasses * homePossession) / 100;
        int awayPasses = totalMatchPasses - homePasses;

        stats.setHomeTotalPasses(homePasses);
        stats.setAwayTotalPasses(awayPasses);

        stats.setHomeAccuratePasses((int) (homePasses * (random.getRandomInt(75, 88) / 100.0)));
        stats.setAwayAccuratePasses((int) (awayPasses * (random.getRandomInt(75, 88) / 100.0)));

        stats.setHomeShoots(random.getRandomInt(Math.max(stats.getHomeGoals(), 3), 22));
        stats.setAwayShoots(random.getRandomInt(Math.max(stats.getAwayGoals(), 3), 22));

        stats.setHomeShootsOnTarget(random.getRandomInt(stats.getHomeGoals(), stats.getHomeShoots()));
        stats.setAwayShootsOnTarget(random.getRandomInt(stats.getAwayGoals(), stats.getAwayShoots()));

        stats.setHomeCorners(random.getRandomInt(1, 10));
        stats.setAwayCorners(random.getRandomInt(1, 10));

        stats.setHomeGoalkeeperSaves(Math.max(0, stats.getAwayShootsOnTarget() - stats.getAwayGoals()));
        stats.setAwayGoalkeeperSaves(Math.max(0, stats.getHomeShootsOnTarget() - stats.getHomeGoals()));

        int maxMinute = stats.getEvents().stream()
                .mapToInt(MatchEvent::minute)
                .max()
                .orElse(90);

        int stoppageTime = Math.max(0, maxMinute - 90);
        stats.setStoppageTime(stoppageTime);

        stats.calculateCardsFromEvents();
    }
}
