package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class NameGeneratorTest {

    private NameGenerator nameGenerator;

    @BeforeEach
    void setUp() {
        nameGenerator = new NameGenerator();
    }

    @Test
    public void shouldGenerateValidNamesForExistingCountry() {
        String expectedFirstName = nameGenerator.generateFirstName(Country.POLAND);
        String expectedLastName = nameGenerator.generateLastName(Country.POLAND);
        List<String> firstNames = nameGenerator.getFirstNames(Country.POLAND);
        List<String> lastNames = nameGenerator.getLastNames(Country.POLAND);

        assertThat(expectedFirstName).isIn(firstNames);
        assertThat(expectedLastName).isIn(lastNames);
    }

    @Test
    public void shouldGenerateDefaultNames() {
        String expectedFirstName = nameGenerator.generateFirstName(null);
        String expectedLastName = nameGenerator.generateLastName(null);

        assertThat(expectedFirstName).isIn("Adam", "John");
        assertThat(expectedLastName).isIn("Smith", "Pork");
    }

    @Test
    void shouldReturnUnknownWhenListIsNullOrEmptyInRandomElement() {
        String resultNull = nameGenerator.getRandomElement(null);
        String resultEmpty = nameGenerator.getRandomElement(Collections.emptyList());

        assertThat(resultNull).isEqualTo("Unknown");
        assertThat(resultEmpty).isEqualTo("Unknown");
    }
}
