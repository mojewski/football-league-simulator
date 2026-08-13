package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.Player;
import com.github.mojewski.footballleaguesimulator.model.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.Position;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerBuilderTest {

    @Test
    void shouldBuildPlayerCorrect() {

        // GIVEN
        String expectedFirstName = "Robert";
        String expectedLastName = "Lewandowski";
        int expectedPotential = 90;
        int expectedRating = 89;
        Country expectedCountry = Country.POLAND;
        Position expectedPosition = Position.FORWARD;
        int expectedAge = 38;

        // WHEN
        Player player = new PlayerBuilder()
                .setFirstName(expectedFirstName)
                .setLastName(expectedLastName)
                .setPotential(expectedPotential)
                .setRating(expectedRating)
                .setCountry(expectedCountry)
                .setPosition(expectedPosition)
                .setAge(expectedAge)
                .getResult();

        // THEN
        assertNotNull(player, "Player should not be NULL");
        assertEquals(expectedFirstName, player.getFirstName());
        assertEquals(expectedLastName, player.getLastName());
        assertEquals(expectedPotential, player.getPotential());
        assertEquals(expectedRating, player.getRating());
        assertEquals(expectedCountry, player.getCountry());
        assertEquals(expectedPosition, player.getPosition());
        assertEquals(expectedAge, player.getAge());
    }
}
