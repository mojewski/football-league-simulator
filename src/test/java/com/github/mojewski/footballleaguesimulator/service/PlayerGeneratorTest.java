package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        when(nameGenerator.generateFirstName(any(Country.class))).thenReturn("Jan");
        when(nameGenerator.generateLastName(any(Country.class))).thenReturn("Kowalski");

        team = new Team("FC Barcelona", 1000000000, 10, 99);
        retiringPlayer = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .setAttributes(new PlayerAttributes(90, 40, 75, 92))
                .setInjuryChance(50)
                .setCountry(Country.POLAND)
                .setPosition(Position.FORWARD)
                .setTeam(team)
                .setAge(38)
                .setIsRetired(true)
                .getResult();
    }

    @Test
    void shouldGeneratePlayerAttributesCorrectly() {
        Player newPlayer = playerGenerator.generateReplacement(retiringPlayer);

        assertNotNull(newPlayer);
        assertEquals("Jan", newPlayer.getFirstName());
        assertEquals("Kowalski", newPlayer.getLastName());
        assertEquals(retiringPlayer.getTeam(), newPlayer.getTeam());
        assertEquals(retiringPlayer.getPosition(), newPlayer.getPosition());
        assertFalse(newPlayer.getIsRetired());

        assertTrue(newPlayer.getAge() >= 16 && newPlayer.getAge() <= 20);
        assertTrue(newPlayer.getInjuryChance() >= 5 && newPlayer.getInjuryChance() <= 60);

        assertNotNull(newPlayer.getAttributes());
        int potential = newPlayer.getAttributes().getPotential();
        assertTrue(potential >= 5 && potential <= 99);

        assertNotNull(newPlayer.getContract());
        assertTrue(newPlayer.getContract().getSalaryPerYear() >= 500);
    }
}