package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.*;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.event.MatchEventGenerator;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.*;

public class MatchSimulator {
    private final MatchEventGenerator eventGenerator;
    private final MatchStatsGenerator statsGenerator;
    private final RandomNumberGenerator random;

    public MatchSimulator(MatchEventGenerator eventGenerator, MatchStatsGenerator statsGenerator, RandomNumberGenerator random) {
        this.eventGenerator = eventGenerator;
        this.statsGenerator = statsGenerator;
        this.random = random;
    }

    public MatchStats simulateMatch(Match match) {
        MatchOutcome outcome = new MatchOutcome(match);

        StoppageTime stoppage = new StoppageTime(
                random.getRandomInt(0, 3),
                random.getRandomInt(1, 10)
        );

        List<MatchEvent> matchEvents = simulateMatchEvents(
                outcome.getHomeGoals(),
                outcome.getAwayGoals(),
                match.getHomeTeam(),
                match.getAwayTeam(),
                stoppage
        );

        WalkoverResult walkoverStatus = extractWalkoverResult(matchEvents);
        if (walkoverStatus != WalkoverResult.NONE) {
            applyWalkoverOutcome(outcome, walkoverStatus);
        }

        MatchStats stats = new MatchStats(outcome, matchEvents);
        statsGenerator.generateMatchStats(stats, stoppage);

        return stats;
    }

    public List<MatchEvent> simulateMatchEvents(int homeGoals, int awayGoals, Team homeTeam, Team awayTeam, StoppageTime stoppage) {
        List<MatchEvent> eventsSoFar = new ArrayList<>();
        List<PendingEvent> pendingEvents = new ArrayList<>();

        for (int i = 0; i < homeGoals; i++) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.GOAL, homeTeam, true));
        }
        for (int i = 0; i < awayGoals; i++) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.GOAL, awayTeam, false));
        }

        int homeYellows = random.getRandomInt(0, 5);
        for (int i = 0; i < homeYellows; i++) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.YELLOW_CARD, homeTeam, true));
        }
        int awayYellows = random.getRandomInt(0, 5);
        for (int i = 0; i < awayYellows; i++) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.YELLOW_CARD, awayTeam, false));
        }

        if (random.getRandomInt(1, 100) <= 8) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.RED_CARD, homeTeam, true));
        }
        if (random.getRandomInt(1, 100) <= 8) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.RED_CARD, awayTeam, false));
        }

        if (random.getRandomInt(1, 100) <= 5) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.INJURY, homeTeam, true));
        }
        if (random.getRandomInt(1, 100) <= 5) {
            pendingEvents.add(new PendingEvent(eventGenerator.generateEventMinute(stoppage), EventType.INJURY, awayTeam, false));
        }

        int maxHomeSubs = Math.min(random.getRandomInt(3, 5), homeTeam.getActiveLineup().getBench().size());
        for (int i = 0; i < maxHomeSubs; i++) {
            int subMinute = random.getRandomInt(45, 90 + stoppage.secondHalf());
            pendingEvents.add(new PendingEvent(subMinute, EventType.SUBSTITUTION, homeTeam, true));
        }

        int maxAwaySubs = Math.min(random.getRandomInt(3, 5), awayTeam.getActiveLineup().getBench().size());
        for (int i = 0; i < maxAwaySubs; i++) {
            int subMinute = random.getRandomInt(45, 90 + stoppage.secondHalf());
            pendingEvents.add(new PendingEvent(subMinute, EventType.SUBSTITUTION, awayTeam, false));
        }

        pendingEvents.sort(Comparator.comparingInt(PendingEvent::minute));

        for (PendingEvent pending : pendingEvents) {
            generateEventForTeam(
                    pending.team(),
                    pending.minute(),
                    eventsSoFar,
                    pending.type(),
                    pending.isHome(),
                    stoppage
            );

            if (pending.type() == EventType.INJURY) {
                handleInjurySubstitution(pending.team(), pending.minute(), eventsSoFar, pending.isHome());
            }

            WalkoverResult walkover = checkWalkover(pending.minute(), homeTeam, awayTeam, eventsSoFar);
            if (walkover != WalkoverResult.NONE) {
                EventType walkoverType = (walkover == WalkoverResult.HOME_WIN) ? EventType.HOME_WALKOVER : EventType.AWAY_WALKOVER;
                Team winningTeam = (walkover == WalkoverResult.HOME_WIN) ? homeTeam : awayTeam;

                eventsSoFar.add(new MatchEvent(pending.minute(), walkoverType, winningTeam, null, null, walkover == WalkoverResult.HOME_WIN));
                break;
            }
        }

        return getSortedEvents(eventsSoFar);
    }

    public void generateEventForTeam(Team team, int minute, List<MatchEvent> eventsSoFar, EventType type, boolean isHomeTeam, StoppageTime stoppage) {
        List<Player> startingLineup = team.getActiveLineup().getStartingEleven();
        List<Player> activePlayers = getActivePlayersForMinute(minute, team, startingLineup, eventsSoFar);
        List<Player> availableBench = getAvailableBenchForTeam(team, eventsSoFar);

        MatchEvent createdEvent = switch (type) {
            case GOAL -> eventGenerator.generateGoalEvent(team, minute, isHomeTeam, activePlayers);
            case YELLOW_CARD, RED_CARD -> eventGenerator.generateCardEvent(team, minute, type, isHomeTeam, activePlayers);
            case INJURY -> eventGenerator.generateInjuryEvent(team, minute, isHomeTeam, activePlayers);
            case SUBSTITUTION -> eventGenerator.generateSubstitutionEvent(team, minute, isHomeTeam, activePlayers, availableBench);
            default -> null;
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

    private void handleInjurySubstitution(Team team, int minute, List<MatchEvent> eventsSoFar, boolean isHomeTeam) {
        List<Player> availableBench = getAvailableBenchForTeam(team, eventsSoFar);

        if (availableBench.isEmpty()) {
            return;
        }

        List<Player> activePlayers = getActivePlayersForMinute(minute, team, team.getActiveLineup().getStartingEleven(), eventsSoFar);

        MatchEvent subEvent = eventGenerator.generateSubstitutionEvent(
                team,
                minute,
                isHomeTeam,
                activePlayers,
                availableBench
        );

        if (subEvent != null) {
            eventsSoFar.add(subEvent);
        }
    }

    public WalkoverResult checkWalkover(int minute, Team homeTeam, Team awayTeam, List<MatchEvent> eventsSoFar) {
        int homeActive = getActivePlayersForMinute(minute, homeTeam, homeTeam.getActiveLineup().getStartingEleven(), eventsSoFar).size();
        int awayActive = getActivePlayersForMinute(minute, awayTeam, awayTeam.getActiveLineup().getStartingEleven(), eventsSoFar).size();

        if (awayActive < 7) {
            return WalkoverResult.HOME_WIN;
        }
        if (homeActive < 7) {
            return WalkoverResult.AWAY_WIN;
        }
        return WalkoverResult.NONE;
    }

    private WalkoverResult extractWalkoverResult(List<MatchEvent> events) {
        for (MatchEvent event : events) {
            if (event.type() == EventType.HOME_WALKOVER) return WalkoverResult.HOME_WIN;
            if (event.type() == EventType.AWAY_WALKOVER) return WalkoverResult.AWAY_WIN;
        }
        return WalkoverResult.NONE;
    }

    private void applyWalkoverOutcome(MatchOutcome outcome, WalkoverResult walkover) {
        if (walkover == WalkoverResult.HOME_WIN) {
            outcome.setHomeGoals(Math.max(3, outcome.getHomeGoals()));
            outcome.setAwayGoals(0);
        } else if (walkover == WalkoverResult.AWAY_WIN) {
            outcome.setHomeGoals(0);
            outcome.setAwayGoals(Math.max(3, outcome.getAwayGoals()));
        }

        outcome.setWalkover(true);
    }

    public List<MatchEvent> getSortedEvents(List<MatchEvent> events) {
        return events.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(MatchEvent::minute))
                .toList();
    }

    public enum WalkoverResult {
        NONE, HOME_WIN, AWAY_WIN
    }

    private record PendingEvent(int minute, EventType type, Team team, boolean isHome) {}
}