package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public record MatchEvent(
        int minute,
        EventType type,
        Team team,
        Player primaryPlayer,
        Player secondaryPlayer,
        boolean isHomeTeam
) {}
