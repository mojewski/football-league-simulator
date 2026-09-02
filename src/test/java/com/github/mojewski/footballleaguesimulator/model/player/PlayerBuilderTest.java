package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBuilderTest {

    @Test
    void shouldBuildPlayerCorrectly() {

        String expectedFirstName = "Robert";
        String expectedLastName = "Lewandowski";
        PlayerAttributes expectedAttributes = new PlayerAttributes(90, 40, 75, 85, 88, 80, 92);
        int expectedInjuryChance = 50;
        Country expectedCountry = Country.POLAND;
        Position expectedPosition = Position.FORWARD;
        Team expectedTeam = new Team("FC Barcelona", 1000000000, 10, 99, Formation.F_3_5_2);
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
                .build();

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
        assertTrue(player.getOverall() > 0);
    }
}