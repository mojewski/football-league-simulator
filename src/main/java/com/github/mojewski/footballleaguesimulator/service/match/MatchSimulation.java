package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.*;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.event.MatchEventGenerator;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.*;
import java.util.function.BiFunction;

public class MatchSimulation {
    private final MatchEventGenerator eventGenerator;
    private final MatchStatsGenerator statsGenerator;
    private final RandomNumberGenerator random;

    public MatchSimulation(MatchEventGenerator eventGenerator, MatchStatsGenerator statsGenerator, RandomNumberGenerator random) {
        this.eventGenerator = eventGenerator;
        this.statsGenerator = statsGenerator;
        this.random = random;
    }

    public MatchStats simulateMatch(Match match) {
        MatchOutcome outcome = new MatchOutcome(match);

        List<MatchEvent> matchEvents = simulateMatchEvents(
                outcome.getHomeGoals(),
                outcome.getAwayGoals(),
                match.getHomeTeam(),
                match.getAwayTeam()
        );

        MatchStats stats = new MatchStats(outcome, matchEvents);
        statsGenerator.generateMatchStats(stats);

        return stats;
    }

    public List<MatchEvent> simulateMatchEvents(int homeGoals, int awayGoals, Team homeTeam, Team awayTeam) {
        List<MatchEvent> eventsSoFar = new ArrayList<>();

        for (int i = 0; i < homeGoals; i++) {
            generateEventForTeam(homeTeam, homeTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.GOAL, true);
        }
        for (int i = 0; i < awayGoals; i++) {
            generateEventForTeam(awayTeam, awayTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.GOAL, false);
        }

        int homeYellows = random.getRandomInt(0, 5);
        for (int i = 0; i < homeYellows; i++) {
            generateEventForTeam(homeTeam, homeTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.YELLOW_CARD, true);
        }
        int awayYellows = random.getRandomInt(0, 5);
        for (int i = 0; i < awayYellows; i++) {
            generateEventForTeam(awayTeam, awayTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.YELLOW_CARD, false);
        }

        if (random.getRandomInt(1, 100) <= 8) {
            generateEventForTeam(homeTeam, homeTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.RED_CARD, true);
        }
        if (random.getRandomInt(1, 100) <= 8) {
            generateEventForTeam(awayTeam, awayTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.RED_CARD, false);
        }

        int homeSubstitutions = random.getRandomInt(3, 5);
        for (int i = 0; i < homeSubstitutions; i++) {
            generateEventForTeam(homeTeam, homeTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.SUBSTITUTION, true);
        }

        int awaySubstitutions = random.getRandomInt(3, 5);
        for (int i = 0; i < awaySubstitutions; i++) {
            generateEventForTeam(awayTeam, awayTeam.getActiveLineup().getStartingEleven(), eventsSoFar, EventType.SUBSTITUTION, false);
        }

        return getSortedEvents(eventsSoFar);
    }

    public void generateEventForTeam(Team team, List<Player> startingLineup, List<MatchEvent> eventsSoFar, EventType type, boolean isHomeTeam) {
        BiFunction<Integer, Team, List<Player>> activeProvider =
                (minute, t) -> getActivePlayersForMinute(minute, t, startingLineup, eventsSoFar);

        List<Player> availableBench = getAvailableBenchForTeam(team, eventsSoFar);

        MatchEvent createdEvent = switch (type) {
            case GOAL -> eventGenerator.generateGoalEvent(team, isHomeTeam, activeProvider);
            case YELLOW_CARD, RED_CARD -> eventGenerator.generateCardEvent(team, type, isHomeTeam, activeProvider);
            case INJURY -> eventGenerator.generateInjuryEvent(team, isHomeTeam, activeProvider);
            case SUBSTITUTION -> eventGenerator.generateSubstitutionEvent(team, isHomeTeam, activeProvider, availableBench);
        };

        if (createdEvent != null) {
            eventsSoFar.add(createdEvent);
        }
    }

    public List<Player> getActivePlayersForMinute(int minute, Team team, List<Player> startingLineup, List<MatchEvent> eventsSoFar) {
        Set<Player> active = new HashSet<>(startingLineup);

        List<MatchEvent> sortedEvents = eventsSoFar.stream()
                .sorted(Comparator.comparingInt(MatchEvent::minute))
                .toList();

        for (MatchEvent event : sortedEvents) {
            if (event.minute() > minute) {
                break;
            }
            if (!event.team().equals(team)) continue;

            if (event.type() == EventType.RED_CARD || event.type() == EventType.INJURY) {
                active.remove(event.primaryPlayer());
            } else if (event.type() == EventType.SUBSTITUTION) {
                active.remove(event.primaryPlayer());
                active.add(event.secondaryPlayer());
            }
        }

        return new ArrayList<>(active);
    }

    private List<Player> getAvailableBenchForTeam(Team team, List<MatchEvent> eventsSoFar) {
        List<Player> fullBench = team.getActiveLineup().getBench();

        Set<Player> usedPlayers = new HashSet<>();
        for (MatchEvent event : eventsSoFar) {
            if (event.team().equals(team) && event.type() == EventType.SUBSTITUTION) {
                usedPlayers.add(event.secondaryPlayer());
            }
        }

        return fullBench.stream()
                .filter(p -> !usedPlayers.contains(p))
                .toList();
    }

    public List<MatchEvent> getSortedEvents(List<MatchEvent> events) {
        return events.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(MatchEvent::minute))
                .toList();
    }
}