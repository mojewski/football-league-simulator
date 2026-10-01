package com.github.mojewski.footballleaguesimulator.service.player;

import com.github.mojewski.footballleaguesimulator.data.NameRepository;
import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class NameGeneratorTest {

    private NameGenerator nameGenerator;
    private NameRepository nameRepository;
    private RandomNumberGenerator random;

    @BeforeEach
    void setUp() {
        nameRepository = new NameRepository();
        random = new RandomNumberGenerator();
        nameGenerator = new NameGenerator(nameRepository, random);
    }

    @Test
    void shouldThrowExceptionWhenNameRepositoryIsNull() {
        assertThatThrownBy(() -> new NameGenerator(null, random))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("NameRepository cannot be null");
    }

    @Test
    void shouldGenerateValidNamesForExistingCountry() {
        String expectedFirstName = nameGenerator.generateFirstName(Country.POLAND);
        String expectedLastName = nameGenerator.generateLastName(Country.POLAND);
        List<String> firstNames = nameRepository.getFirstNamesForCountry(Country.POLAND);
        List<String> lastNames = nameRepository.getLastNamesForCountry(Country.POLAND);

        assertThat(expectedFirstName).isIn(firstNames);
        assertThat(expectedLastName).isIn(lastNames);
    }

    @Test
    void shouldGenerateDefaultNames() {
        String expectedFirstName = nameGenerator.generateFirstName(null);
        String expectedLastName = nameGenerator.generateLastName(null);

        List<String> defaultFirstNames = nameRepository.getFirstNamesForCountry(null);
        List<String> defaultLastNames = nameRepository.getLastNamesForCountry(null);

        assertThat(expectedFirstName).isIn(defaultFirstNames);
        assertThat(expectedLastName).isIn(defaultLastNames);
    }

    @Test
    void shouldReturnUnknownWhenListIsNullOrEmptyInRandomElement() {
        String resultNull = nameGenerator.getRandomElement(null);
        String resultEmpty = nameGenerator.getRandomElement(Collections.emptyList());

        assertThat(resultNull).isEqualTo("Unknown");
        assertThat(resultEmpty).isEqualTo("Unknown");
    }
}
