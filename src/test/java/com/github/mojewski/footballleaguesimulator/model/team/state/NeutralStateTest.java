package com.github.mojewski.footballleaguesimulator.model.team.state;

import com.github.mojewski.footballleaguesimulator.model.match.MatchResult;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeutralStateTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("FC Test", 1_000_000, 60, 7, Formation.F_3_5_2);
    }

    @Test
    public void ShouldChangeStateToCrisisAfterThreeLossesInRow() {
        team.updateMorale(MatchResult.LOSS);
        team.updateMorale(MatchResult.LOSS);
        team.updateMorale(MatchResult.LOSS);

        assertThat(team.getCurrentState()).isInstanceOf(CrisisState.class);
    }

    @Test
    public void ShouldChangeStateToHighMoraleAfterThreeWinsInRow() {
        team.updateMorale(MatchResult.WIN);
        team.updateMorale(MatchResult.WIN);
        team.updateMorale(MatchResult.WIN);

        assertThat(team.getCurrentState()).isInstanceOf(HighMoraleState.class);
    }

    @Test
    public void ShouldNotChangeStateAfterLossesAndWinsNotInRow() {
        team.updateMorale(MatchResult.LOSS);
        team.updateMorale(MatchResult.LOSS);
        team.updateMorale(MatchResult.DRAW);
        team.updateMorale(MatchResult.LOSS);

        assertThat(team.getCurrentState()).isInstanceOf(NeutralState.class);

        team.updateMorale(MatchResult.WIN);
        team.updateMorale(MatchResult.WIN);
        team.updateMorale(MatchResult.LOSS);
        team.updateMorale(MatchResult.WIN);

        assertThat(team.getCurrentState()).isInstanceOf(NeutralState.class);
    }
}
