package com.github.mojewski.footballleaguesimulator.model.team.team_state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public interface TeamMoraleState {

    void onMatchEnd(Team team, MatchResult result);

    void getMoraleModifier();
}
