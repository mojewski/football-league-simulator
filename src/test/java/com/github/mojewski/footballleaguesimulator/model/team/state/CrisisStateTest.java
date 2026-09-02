package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.model.team.team_state.CrisisState;
import com.github.mojewski.footballleaguesimulator.model.team.team_state.NeutralState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CrisisStateTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("FC Test", 1_000_000, 60, 7, Formation.F_3_5_2);
        team.setTeamState(new CrisisState());
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
    public void ShouldChangeStateToNeutralCorrectlyAfterWin() {
        team.updateMorale(MatchResult.WIN);

        assertThat(team.getCurrentState()).isInstanceOf(NeutralState.class);

        NeutralState currentState = (NeutralState) team.getCurrentState();
        assertEquals(0, currentState.getLossesInRow());
        assertEquals(1, currentState.getWinsInRow());
    }
}
