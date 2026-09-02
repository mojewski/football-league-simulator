package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlayerGeneratorTest {

    @Mock
    private NameGenerator nameGenerator;
    @Mock
    private RandomNumberGenerator random;

    private PlayerGenerator playerGenerator;
    private Team team;
    private Player retiringPlayer;

    @BeforeEach
    void setUp() {
        playerGenerator = new PlayerGenerator(nameGenerator, random);

        team = new Team("FC Barcelona", 1000000000, 10, 99, Formation.F_3_5_2);
        retiringPlayer = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .setAttributes(new PlayerAttributes(90, 40, 75, 82, 85, 80, 92))
                .setInjuryChance(50)
                .setCountry(Country.POLAND)
                .setPosition(Position.FORWARD)
                .setTeam(team)
                .setAge(38)
                .build();

        when(nameGenerator.generateFirstName(any())).thenReturn("Jan");
        when(nameGenerator.generateLastName(any())).thenReturn("Kowalski");
    }

    @Test
    void shouldGeneratePlayerAttributesCorrectly() {
        when(random.getRandomInt(16, 20)).thenReturn(18);
        when(random.getRandomInt(5, 60)).thenReturn(20);
        when(random.getRandomInt(20, 99)).thenReturn(80);
        when(random.getRandomInt(-10, 10)).thenReturn(0);
        when(random.getRandomDouble(0.6, 0.76)).thenReturn(0.7);
        when(random.getRandomDouble(0.90, 1.10)).thenReturn(1.0);

        Player newPlayer = playerGenerator.generateReplacement(retiringPlayer);

        assertNotNull(newPlayer);
        assertEquals("Jan", newPlayer.getFirstName());
        assertEquals("Kowalski", newPlayer.getLastName());
        assertEquals(retiringPlayer.getTeam(), newPlayer.getTeam());
        assertEquals(retiringPlayer.getPosition(), newPlayer.getPosition());
        assertFalse(newPlayer.isRetired());

        assertEquals(18, newPlayer.getAge());
        assertEquals(20, newPlayer.getInjuryChance());

        assertNotNull(newPlayer.getAttributes());
        assertNotNull(newPlayer.getContract());
        assertTrue(newPlayer.getContract().getSalaryPerYear() >= 500);
    }

    @Test
    void shouldGenerateBetterPotentialForBetterAcademy() {
        Team topAcademyTeam = new Team("Academy 99", 1_000_000, 99, 99, Formation.F_3_5_2);
        Player topAcademyRetiringPlayer = new PlayerBuilder()
                .setFirstName("Jan")
                .setLastName("Nowak")
                .setCountry(Country.POLAND)
                .setPosition(Position.FORWARD)
                .setTeam(topAcademyTeam)
                .setAttributes(new PlayerAttributes(50, 50, 50, 50, 50, 50, 50))
                .build();

        when(random.getRandomInt(16, 20)).thenReturn(18);
        when(random.getRandomInt(5, 60)).thenReturn(20);
        when(random.getRandomInt(20, 99)).thenReturn(80);
        when(random.getRandomInt(-10, 10)).thenReturn(0);
        when(random.getRandomDouble(0.6, 0.76)).thenReturn(0.7);
        when(random.getRandomDouble(0.90, 1.10)).thenReturn(1.0);

        Player playerFromTopAcademy = playerGenerator.generateReplacement(topAcademyRetiringPlayer);
        Player playerFromWeakAcademy = playerGenerator.generateReplacement(retiringPlayer);

        assertTrue(playerFromTopAcademy.getAttributes().getPotential() >
                playerFromWeakAcademy.getAttributes().getPotential());
    }

    @Test
    void shouldGenerateGoalkeeperWithGoalkeeperAttributes() {
        Player retiringGoalkeeper = new PlayerBuilder()
                .setFirstName("Wojciech")
                .setLastName("Szczęsny")
                .setCountry(Country.POLAND)
                .setPosition(Position.GOALKEEPER)
                .setTeam(team)
                .setAttributes(new PlayerAttributes(85, 82, 70, 60, 78, 88))
                .build();

        when(random.getRandomInt(16, 20)).thenReturn(18);
        when(random.getRandomInt(5, 60)).thenReturn(10);
        when(random.getRandomInt(20, 99)).thenReturn(90);
        when(random.getRandomInt(-10, 10)).thenReturn(0);
        when(random.getRandomDouble(0.6, 0.76)).thenReturn(0.7);
        when(random.getRandomDouble(0.90, 1.10)).thenReturn(1.0);

        Player newGoalkeeper = playerGenerator.generateReplacement(retiringGoalkeeper);

        assertNotNull(newGoalkeeper);
        assertEquals(Position.GOALKEEPER, newGoalkeeper.getPosition());

        PlayerAttributes attributes = newGoalkeeper.getAttributes();
        assertTrue(attributes.getReflex() > 1);
        assertTrue(attributes.getHandling() > 1);
        assertEquals(1, attributes.getShooting());
        assertEquals(1, attributes.getDefending());
        assertEquals(1, attributes.getDribbling());
    }
}