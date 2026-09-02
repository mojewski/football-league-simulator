package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Team testTeam;
    private PlayerAttributes testAttributes;

    @BeforeEach
    void setUp() {
        testTeam = new Team("FC Barcelona", 1_000_000_000, 90, 99, Formation.F_3_4_3);
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
                .setStamina(80);
    }

    @Test
    void shouldCalculateOverallCorrectly() {
        Player player = createBasePlayerBuilder().build();

        assertTrue(player.getOverall() > 0);
        assertEquals(player.getAttributes().calculateOverall(Position.FORWARD), player.getOverall());
    }

    @Test
    void shouldNotRetireYoung() {
        Player player = createBasePlayerBuilder()
                .setAge(20)
                .build();

        assertFalse(player.decideRetirement(1));
        assertFalse(player.isRetired());
    }

    @Test
    void shouldRetireOld() {
        Player player = createBasePlayerBuilder()
                .setAge(41)
                .build();

        assertTrue(player.decideRetirement(100));
        assertTrue(player.isRetired());
    }

    @Test
    void shouldDecideRetirementBasedOnProbabilityRoll() {
        Player player = createBasePlayerBuilder()
                .setAge(35)
                .build();

        assertTrue(player.decideRetirement(5));
        assertTrue(player.isRetired());

        Player activePlayer = createBasePlayerBuilder()
                .setAge(35)
                .build();

        assertFalse(activePlayer.decideRetirement(15));
        assertFalse(activePlayer.isRetired());
    }

    @Test
    void shouldSignContractAndAssignTeamCorrectly() {
        Team firstTeam = new Team("Bayern Munchen", 1_000_000_000, 85, 87, Formation.F_3_4_3);
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
    void shouldTerminateContract() {
        Player player = createBasePlayerBuilder().build();
        PlayerContract contract = new PlayerContract(999_999, 5);
        player.signContract(contract, testTeam);

        player.terminateContract();

        assertNull(player.getTeam());
        assertNull(player.getContract());
        assertFalse(player.hasActiveContract());
    }

    @Test
    void shouldCalculateEffectiveInjuryChanceCorrectly() {
        Player player = createBasePlayerBuilder()
                .setInjuryChance(10)
                .setStamina(80)
                .build();

        assertEquals(player.getInjuryChance(), player.getEffectiveInjuryChance());

        player.drainStamina(50);
        assertTrue(player.getInjuryChance() < player.getEffectiveInjuryChance());
        assertEquals(20, player.getEffectiveInjuryChance());

        player.setStamina(1);
        assertEquals(30, player.getEffectiveInjuryChance());
    }

    @Test
    void shouldClampStaminaWithinLimits() {
        Player player = createBasePlayerBuilder()
                .setStamina(90)
                .build();

        player.regenerateStamina(20);
        assertEquals(99, player.getStamina());

        player.drainStamina(150);
        assertEquals(1, player.getStamina());
    }
}