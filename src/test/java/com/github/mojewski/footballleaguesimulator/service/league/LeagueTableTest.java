package com.github.mojewski.footballleaguesimulator.service.league;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.league.LeagueRules;
import com.github.mojewski.footballleaguesimulator.model.league.tables.LeagueTable;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeagueTableTest {
    LeagueTable table;

    @BeforeEach
    void setUp() {
        table = new LeagueTable();
        for(int i = 0; i < 18; i++) {
            Team team = new Team("FC Test " + i);
            table.addTeam(team);
        }
    }

    @Test
    void shouldGenerateTableCorrectly() {
        table.updateTable();

        for(int i = 1; i <= 18; i++) {
            assertEquals(0, table.getTeamAtPosition(i).getTeamStats().getPoints());
            table.getTeamAtPosition(i).getTeamStats().addWin();
        }

        table.updateTable();

        for(int i = 1; i <= 18; i++) {
            assertEquals(3, table.getTeamAtPosition(i).getTeamStats().getPoints());
        }

        table.getTeamAtPosition(5).getTeamStats().addDraw();
        Team specificTeam = table.getTeamAtPosition(5);
        table.updateTable();

        assertEquals(specificTeam, table.getTeamAtPosition(1));
    }

    @Test
    void shouldGetTeamsInSpecificZonesCorrectly() {
        for(int i = 3; i <= 18; i++) {
            table.getTeamAtPosition(i).getTeamStats().addWin();
        }
        table.getTeamAtPosition(7).getTeamStats().addWin();

        Team expectedRelegationZoneTeam1 = table.getTeamAtPosition(1);
        Team expectedRelegationZoneTeam2 = table.getTeamAtPosition(2);
        Team expectedPromotionZoneTeam = table.getTeamAtPosition(7);

        table.updateTable();

        assertEquals(expectedPromotionZoneTeam, table.getTeamsInStatusZone(1, 1).getFirst());
        assertEquals(expectedRelegationZoneTeam1, table.getTeamsInStatusZone(17, 2).getFirst());
        assertEquals(expectedRelegationZoneTeam2, table.getTeamsInStatusZone(17, 2).getLast());
    }
}
