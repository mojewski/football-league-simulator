package com.github.mojewski.footballleaguesimulator.model.league.schedule;

import java.util.List;
import java.util.Optional;

public class LeagueSchedule {

    private final List<Matchday> matchdays;
    private int currentMatchdayIndex;

    public LeagueSchedule(List<Matchday> matchdays) {
        this.matchdays = matchdays;
        this.currentMatchdayIndex = 0;
    }

    public List<Matchday> getMatchdays() {
        return matchdays;
    }

    public Optional<Matchday> getCurrentMatchday() {
        if (isSeasonFinished()) {
            return Optional.empty();
        }
        return Optional.of(matchdays.get(currentMatchdayIndex));
    }

    public void advanceToNextMatchday() {
        if (!isSeasonFinished()) {
            currentMatchdayIndex++;
        }
    }

    public boolean isSeasonFinished() {
        return currentMatchdayIndex >= matchdays.size();
    }

    public Matchday getMatchdayByNumber(int number) {
        if (number < 1 || number > matchdays.size()) {
            throw new IllegalArgumentException("Incorrect matchday number: " + number);
        }
        return matchdays.get(number - 1);
    }
}