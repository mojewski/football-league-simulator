package com.github.mojewski.footballleaguesimulator.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerAttributesTest {

    @Test
    void ShouldDecreaseSkillsCorrectly() {

        PlayerAttributes attributes = new PlayerAttributes(5, 5, 5, 5, 5, 5, 99);

        attributes.decreaseSkills(100, Position.FORWARD);

        assertEquals(1, attributes.getShooting());
        assertEquals(1, attributes.getPassing());
        assertEquals(1, attributes.getDefending());
        assertEquals(1, attributes.getPace());
        assertEquals(1, attributes.getDribbling());
        assertEquals(1, attributes.getPhysical());
    }

    @Test
    void ShouldNotIncreaseSkillsAfterBadSeason() {

        PlayerAttributes attributes = new PlayerAttributes(5, 5, 5, 5, 5, 5, 99);

        attributes.increaseSkills(4, Position.FORWARD);

        assertEquals(5, attributes.getShooting());
        assertEquals(5, attributes.getPassing());
        assertEquals(5, attributes.getDefending());
        assertEquals(5, attributes.getPace());
        assertEquals(5, attributes.getDribbling());
        assertEquals(5, attributes.getPhysical());
    }

    @Test
    void ShouldIncreaseSkillsCorrectly() {

        PlayerAttributes attributes = new PlayerAttributes(80, 80, 80, 80, 80, 80, 82);

        attributes.increaseSkills(10, Position.FORWARD);

        assertTrue(attributes.getShooting() > 80 || attributes.getPassing() > 80 || attributes.getDefending() > 80
                || attributes.getPace() > 80 || attributes.getDribbling() > 80 || attributes.getPhysical() > 80);
        assertTrue(attributes.calculateOverall(Position.FORWARD) <= attributes.getPotential());
    }

    @Test
    void shouldIgnoreNegativeOrZeroDropAmount() {
        PlayerAttributes attributes = new PlayerAttributes(50, 50, 50, 50, 50, 50, 99);

        attributes.decreaseSkills(-5, Position.FORWARD);

        assertEquals(50, attributes.getShooting());
        assertEquals(50, attributes.getPassing());
        assertEquals(50, attributes.getDefending());
        assertEquals(50, attributes.getPace());
        assertEquals(50, attributes.getDribbling());
        assertEquals(50, attributes.getPhysical());
    }
}