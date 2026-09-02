package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class HighMoraleState implements TeamMoraleState {

    @Override
    public void onMatchEnd(Team team, MatchResult result) {

        if(result == MatchResult.LOSS) {
            team.setTeamState(new NeutralState(0, 1));
        } else if(result == MatchResult.DRAW) {
            team.setTeamState(new NeutralState());
        }
    }

    @Override
    public double getMoraleModifier() {
        return 1.25;
    }
}
