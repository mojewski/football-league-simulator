package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.service.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerAttributesTest {

    private RandomNumberGenerator random;

    @BeforeEach
    void setUp() {
        random = new RandomNumberGenerator();
    }

    @Test
    void shouldDecreaseFieldPlayerSkillsCorrectly() {
        PlayerAttributes attributes = new PlayerAttributes(5, 5, 5, 5, 5, 5, 99);

        attributes.decreaseSkills(100, Position.FORWARD, random);

        assertEquals(1, attributes.getShooting());
        assertEquals(1, attributes.getPassing());
        assertEquals(1, attributes.getDefending());
        assertEquals(1, attributes.getPace());
        assertEquals(1, attributes.getDribbling());
        assertEquals(1, attributes.getPhysical());
    }

    @Test
    void shouldDecreaseGoalkeeperSkillsCorrectly() {
        PlayerAttributes attributes = new PlayerAttributes(5, 5, 5, 5, 5, 99);

        attributes.decreaseSkills(100, Position.GOALKEEPER, random);

        assertEquals(1, attributes.getReflex());
        assertEquals(1, attributes.getHandling());
        assertEquals(1, attributes.getPassing());
        assertEquals(1, attributes.getPace());
        assertEquals(1, attributes.getPhysical());
    }

    @Test
    void shouldNotIncreaseSkillsAfterBadSeason() {
        PlayerAttributes attributes = new PlayerAttributes(5, 5, 5, 5, 5, 5, 99);

        attributes.increaseSkills(4, Position.FORWARD, random);

        assertEquals(5, attributes.getShooting());
        assertEquals(5, attributes.getPassing());
        assertEquals(5, attributes.getDefending());
        assertEquals(5, attributes.getPace());
        assertEquals(5, attributes.getDribbling());
        assertEquals(5, attributes.getPhysical());
    }

    @Test
    void shouldIncreaseFieldPlayerSkillsCorrectly() {
        PlayerAttributes attributes = new PlayerAttributes(80, 80, 80, 80, 80, 80, 85);

        attributes.increaseSkills(10, Position.FORWARD, random);

        assertTrue(attributes.getShooting() > 80 || attributes.getPassing() > 80 || attributes.getDefending() > 80
                || attributes.getPace() > 80 || attributes.getDribbling() > 80 || attributes.getPhysical() > 80);
        assertTrue(attributes.calculateOverall(Position.FORWARD) <= attributes.getPotential());
    }

    @Test
    void shouldIncreaseGoalkeeperSkillsCorrectly() {
        PlayerAttributes attributes = new PlayerAttributes(80, 80, 80, 80, 80, 85);

        attributes.increaseSkills(10, Position.GOALKEEPER, random);

        assertTrue(attributes.getReflex() > 80 || attributes.getHandling() > 80 || attributes.getPassing() > 80
                || attributes.getPace() > 80 || attributes.getPhysical() > 80);
        assertTrue(attributes.calculateOverall(Position.GOALKEEPER) <= attributes.getPotential());
    }

    @Test
    void shouldIgnoreNegativeOrZeroDropAmount() {
        PlayerAttributes attributes = new PlayerAttributes(50, 50, 50, 50, 50, 50, 99);

        attributes.decreaseSkills(-5, Position.FORWARD, random);

        assertEquals(50, attributes.getShooting());
        assertEquals(50, attributes.getPassing());
        assertEquals(50, attributes.getDefending());
        assertEquals(50, attributes.getPace());
        assertEquals(50, attributes.getDribbling());
        assertEquals(50, attributes.getPhysical());
    }

    @Test
    void shouldCalculateStaminaModifierCorrectly() {
        PlayerAttributes attributes = new PlayerAttributes(50, 50, 50, 50, 50, 50, 99);

        assertEquals(1.0, attributes.getStaminaModifier(70));
        assertEquals(1.0, attributes.getStaminaModifier(99));
        assertTrue(attributes.getStaminaModifier(35) < 1.0);
    }
}