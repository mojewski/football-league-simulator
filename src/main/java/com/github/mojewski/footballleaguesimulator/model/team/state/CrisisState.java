package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class CrisisState implements TeamMoraleState {

    @Override
    public void onMatchEnd(Team team, MatchResult result) {

        if(result == MatchResult.WIN) {
            team.setTeamState(new NeutralState(1, 0));
        } else if(result == MatchResult.DRAW) {
            team.setTeamState(new NeutralState());
        }
    }

    @Override
    public double getMoraleModifier() {
        return 0.75;
    }
}
