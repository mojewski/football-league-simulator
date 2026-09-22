package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.event.MatchEventGenerator;

import java.util.*;

public class MatchSimulation {
    //dodac inne eventy i dokonczyc klase

    private final MatchEventGenerator eventGenerator;

    public MatchSimulation(MatchEventGenerator eventGenerator) {
        this.eventGenerator = eventGenerator;
    }

    public void generateMatchEvents(Team homeTeam, Team awayTeam, List<Player> homeStartingLineup, List<Player> awayStartingLineup, List<MatchEvent> eventsSoFar) {

        MatchEvent homeGoal = eventGenerator.generateGoalEvent(
                homeTeam,
                true,
                (minute, t) -> getActivePlayersForMinute(minute, t, homeStartingLineup, eventsSoFar)
        );
        eventsSoFar.add(homeGoal);

        MatchEvent awayGoal = eventGenerator.generateGoalEvent(
                awayTeam,
                false,
                (minute, t) -> getActivePlayersForMinute(minute, t, awayStartingLineup, eventsSoFar)
        );
        eventsSoFar.add(awayGoal);
    }

    public List<Player> getActivePlayersForMinute(int minute, Team team, List<Player> startingLineup, List<MatchEvent> eventsSoFar) {
        Set<Player> active = new HashSet<>(startingLineup);

        List<MatchEvent> sortedEvents = eventsSoFar.stream()
                .sorted(Comparator.comparingInt(MatchEvent::minute))
                .toList();

        for (MatchEvent event : eventsSoFar) {
            if (event.minute() > minute) continue;
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
}