package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LeagueTableTest {
    private LeagueTable table;

    @BeforeEach
    void setUp() {
        this.table = new LeagueTable();
        for(int i = 0; i < 17; i++) {
            Team team = new Team("FC " + i);
            table.addTeam(team);
            team.getTeamStats().addDraw();
        }
    }

    @Test
    void shouldGenerateTableWithCorrectRow() {
        Team testTeam = new Team("TEST TEAM");
        table.addTeam(testTeam);

        table.updateTable();

        assertEquals(testTeam, table.getTeamAtPosition(18));

        testTeam.getTeamStats().addWin();
        table.updateTable();

        assertEquals(testTeam, table.getTeamAtPosition(1));
    }

    @Test
    void shouldNotLetGetIncorrectPositionOfTeam() {
        table.updateTable();

        assertNull(table.getTeamAtPosition(30));
        assertNull(table.getTeamAtPosition(-2));
        assertNull(table.getTeamAtPosition(0));
    }

    @Test
    void shouldGetListOfTeamsInCorrectStatusZone() {
        Team testTeam1 = new Team("TEST TEAM1");
        table.addTeam(testTeam1);
        Team testTeam2 = new Team("TEST TEAM2");
        table.addTeam(testTeam2);
        Team testTeam3 = new Team("TEST TEAM3");
        table.addTeam(testTeam3);

        table.updateTable();

        assertThat(table.getTeamsInStatusZone(18, 20)).containsExactlyInAnyOrder(testTeam1, testTeam2, testTeam3);

        testTeam1.getTeamStats().addWin();
        testTeam3.getTeamStats().addWin();

        table.updateTable();

        assertThat(table.getTeamsInStatusZone(1, 2)).containsExactlyInAnyOrder(testTeam1, testTeam3);
    }
}
