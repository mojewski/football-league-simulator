package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighMoraleStateTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("FC Test", 1_000_000, 60, 7, Formation.F_3_5_2);
        team.setTeamState(new HighMoraleState());
    }

    @Test
    public void ShouldChangeStateToNeutralCorrectlyAfterDraw() {
        team.updateMorale(MatchResult.DRAW);

        assertThat(team.getCurrentState()).isInstanceOf(NeutralState.class);

        NeutralState currentState = (NeutralState) team.getCurrentState();
        assertEquals(0, currentState.getLossesInRow());
        assertEquals(0, currentState.getWinsInRow());
    }

    @Test
    public void ShouldChangeStateToNeutralCorrectlyAfterLoss() {
        team.updateMorale(MatchResult.LOSS);

        assertThat(team.getCurrentState()).isInstanceOf(NeutralState.class);

        NeutralState currentState = (NeutralState) team.getCurrentState();
        assertEquals(1, currentState.getLossesInRow());
        assertEquals(0, currentState.getWinsInRow());
    }
}
