package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

public class NeutralState implements TeamMoraleState {

    private int winsInRow;
    private int lossesInRow;

    public NeutralState(){}

    public NeutralState(int initialWins, int initialLosses) {
        this.winsInRow = initialWins;
        this.lossesInRow = initialLosses;
    }

    @Override
    public void onMatchEnd(Team team, MatchResult result) {

        if(result == MatchResult.WIN) {
            this.lossesInRow = 0;
            this.winsInRow++;
            if(this.winsInRow >= 3) team.setTeamState(new HighMoraleState());
        }
        else if(result == MatchResult.LOSS) {
            this.winsInRow = 0;
            this.lossesInRow++;
            if(this.lossesInRow >= 3) team.setTeamState(new CrisisState());
        }
        else {
            this.winsInRow = 0;
            this.lossesInRow = 0;
        }
    }

    @Override
    public double getMoraleModifier() {
        return 1.0;
    }

    public int getWinsInRow() { return winsInRow; }
    public int getLossesInRow() { return lossesInRow; }
}
