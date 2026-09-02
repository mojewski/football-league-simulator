package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.player.state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.MatchLineup;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LineupGeneratorTest {

    private Team testTeam;
    private LineupGenerator lineupGenerator = new LineupGenerator();

    @BeforeEach
    void setUp() {
        testTeam = new Team("FC Test", 1_000, 99, 99, Formation.F_3_5_2);
        createAndAddPlayers(Position.GOALKEEPER, 5);
        createAndAddPlayers(Position.DEFENDER, 10);
        createAndAddPlayers(Position.MIDFIELDER, 10);
        createAndAddPlayers(Position.FORWARD, 10);
    }

    private void createAndAddPlayers(Position position, int count) {
        for (int i = 1; i <= count; i++) {
            Player player = new PlayerBuilder()
                    .setPosition(position)
                    .setCurrentState(new AvailableState())
                    .build();
            testTeam.addPlayer(player);
        }
    }

    @Test
    void ShouldCreateCorrectNumberOfPlayersInLineupAndBench() {
        MatchLineup matchLineup = lineupGenerator.generateAutoLineup(testTeam);

        assertEquals(11, matchLineup.getStartingEleven().size());
        assertEquals(10, matchLineup.getBench().size());
    }

    @Test
    void ShouldCreateCorrectNumberOfPlayersInDifferentFormation() {
        MatchLineup lineup352 = lineupGenerator.generateAutoLineup(testTeam);

        assertEquals(1, lineup352.getStartingPlayersForPosition(Position.GOALKEEPER).size());
        assertEquals(3, lineup352.getStartingPlayersForPosition(Position.DEFENDER).size());
        assertEquals(5, lineup352.getStartingPlayersForPosition(Position.MIDFIELDER).size());
        assertEquals(2, lineup352.getStartingPlayersForPosition(Position.FORWARD).size());

        testTeam.setFormation(Formation.F_4_5_1);
        MatchLineup lineup451 = lineupGenerator.generateAutoLineup(testTeam);

        assertEquals(1, lineup451.getStartingPlayersForPosition(Position.GOALKEEPER).size());
        assertEquals(4, lineup451.getStartingPlayersForPosition(Position.DEFENDER).size());
        assertEquals(5, lineup451.getStartingPlayersForPosition(Position.MIDFIELDER).size());
        assertEquals(1, lineup451.getStartingPlayersForPosition(Position.FORWARD).size());
    }
}
