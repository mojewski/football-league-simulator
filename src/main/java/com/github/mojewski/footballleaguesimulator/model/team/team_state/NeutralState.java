package com.github.mojewski.footballleaguesimulator.model.team.team_state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class NeutralState implements TeamMoraleState {

    private int losesInRow;

    @Override
    public void onMatchEnd(Team team, MatchResult result) {

    }

    @Override
    public void getMoraleModifier() {

    }
}
