package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.data.NameRepository;
import com.github.mojewski.footballleaguesimulator.model.Country;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class NameGenerator {

    private final NameRepository nameRepository;

    public NameGenerator(NameRepository nameRepository) {
        this.nameRepository = Objects.requireNonNull(nameRepository, "NameRepository cannot be null");
    }

    public String generateFirstName(Country country) {
        List<String> names = nameRepository.getFirstNamesForCountry(country);
        return getRandomElement(names);
    }

    public String generateLastName(Country country) {
        List<String> names = nameRepository.getLastNamesForCountry(country);
        return getRandomElement(names);
    }

    public String getRandomElement(List<String> list) {
        if(list == null || list.isEmpty()) { return "Unknown"; }

        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }
}
