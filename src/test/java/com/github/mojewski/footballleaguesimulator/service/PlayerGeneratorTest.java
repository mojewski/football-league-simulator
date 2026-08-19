package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerGeneratorTest {

    @Mock
    private NameGenerator nameGenerator;

    private PlayerGenerator playerGenerator;
    private Team team;
    private Player retiringPlayer;

    @BeforeEach
    void setUp() {
        playerGenerator = new PlayerGenerator(nameGenerator);
        team = new Team("FC Barcelona", 1000000000, 10);
        retiringPlayer = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .setPotential(90)
                .setRating(89)
                .setInjuryChance(50)
                .setCountry(Country.POLAND)
                .setPosition(Position.FORWARD)
                .setTeam(team)
                .setAge(38)
                .setIsRetired(true)
                .getResult();
    }

    @Test
    void shouldGeneratePlayersAttributesCorrectly() {

        Player newPlayer = playerGenerator.generateReplacement(retiringPlayer);

        assertNotNull(newPlayer);
        assertEquals(newPlayer.getTeam(), retiringPlayer.getTeam());
        assertEquals(newPlayer.getPosition(), retiringPlayer.getPosition());
        assertFalse(newPlayer.getIsRetired());
        assertTrue(newPlayer.getAge() >= 16 && newPlayer.getAge() <= 20);
        assertTrue(newPlayer.getPotential() >= 30 && newPlayer.getPotential() <= 99);
        assertTrue(newPlayer.getInjuryChance() >= 5 && newPlayer.getInjuryChance() <= 80);
        assertTrue(newPlayer.getRating() < newPlayer.getPotential());
        assertTrue(newPlayer.getRating() >= (int) (newPlayer.getPotential() * 0.6));
    }
}
