package com.github.mojewski.footballleaguesimulator.model;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBuilderTest {

    @Test
    void shouldBuildPlayerCorrectly() {

        String expectedFirstName = "Robert";
        String expectedLastName = "Lewandowski";
        PlayerAttributes expectedAttributes = new PlayerAttributes(90, 40, 75, 92);
        int expectedInjuryChance = 50;
        Country expectedCountry = Country.POLAND;
        Position expectedPosition = Position.FORWARD;
        Team expectedTeam = new Team("FC Barcelona", 1000000000, 10);
        int expectedAge = 38;

        Player player = new PlayerBuilder()
                .setFirstName(expectedFirstName)
                .setLastName(expectedLastName)
                .setAttributes(expectedAttributes)
                .setInjuryChance(expectedInjuryChance)
                .setCountry(expectedCountry)
                .setPosition(expectedPosition)
                .setTeam(expectedTeam)
                .setAge(expectedAge)
                .setIsRetired(false)
                .getResult();

        assertNotNull(player, "Player should not be NULL");
        assertEquals(expectedFirstName, player.getFirstName());
        assertEquals(expectedLastName, player.getLastName());
        assertEquals(expectedAttributes, player.getAttributes());
        assertEquals(expectedInjuryChance, player.getInjuryChance());
        assertEquals(expectedCountry, player.getCountry());
        assertEquals(expectedPosition, player.getPosition());
        assertEquals(expectedTeam, player.getTeam());
        assertEquals(expectedAge, player.getAge());
        assertFalse(player.getIsRetired());
        assertTrue(player.getOverallRating() > 0);
    }
}