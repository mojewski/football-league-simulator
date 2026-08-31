package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Team testTeam;
    private PlayerAttributes testAttributes;

    @BeforeEach
    void setUp() {
        testTeam = new Team("FC Barcelona", 1000000000, 90, 99);
        testAttributes = new PlayerAttributes(90, 30, 60, 85, 88, 80, 90);
    }

    private PlayerBuilder createBasePlayerBuilder() {
        return new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .setAge(38)
                .setPosition(Position.FORWARD)
                .setAttributes(testAttributes)
                .setCountry(Country.POLAND)
                .setCurrentState(new AvailableState())
                .setIsRetired(false);
    }

    @Test
    void ShouldCalculateOverallCorrectly() {

        Player player = createBasePlayerBuilder().build();

        assertTrue(player.getOverall() > 0);
        assertEquals(player.getAttributes().calculateOverall(Position.FORWARD), player.getOverall());
    }

    @Test
    void ShouldNotRetireYoung() {

        Player player = createBasePlayerBuilder()
                .setAge(20)
                .build();

        assertFalse(player.decideRetirement());
        assertFalse(player.getIsRetired());
    }

    @Test
    void ShouldRetireOld() {

        Player player = createBasePlayerBuilder()
                .setAge(41)
                .build();

        assertTrue(player.decideRetirement());
        assertTrue(player.getIsRetired());
    }

    @Test
    void ShouldSignContractAndAssignTeamCorrectly() {

        Team firstTeam = new Team("Bayern Munchen", 1_000_000_000, 85, 87);
        Player player = createBasePlayerBuilder()
                .setTeam(firstTeam)
                .build();
        PlayerContract contract = new PlayerContract(999_999, 5);
        player.signContract(contract, testTeam);

        assertEquals(testTeam, player.getTeam());
        assertEquals(contract, player.getContract());
        assertTrue(player.hasActiveContract());
    }

    @Test
    void ShouldTerminateContract() {

        Player player = createBasePlayerBuilder().build();
        PlayerContract contract = new PlayerContract(999_999, 5);
        player.signContract(contract, testTeam);
        player.terminateContract();

        assertNull(player.getTeam());
        assertNull(player.getContract());
        assertFalse(player.hasActiveContract());
    }
}