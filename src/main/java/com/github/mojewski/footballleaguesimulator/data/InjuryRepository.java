package com.github.mojewski.footballleaguesimulator.data;

import com.github.mojewski.footballleaguesimulator.model.player.Injury;
import com.github.mojewski.footballleaguesimulator.service.RandomNumberGenerator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InjuryRepository {

    private static final List<Injury> POSSIBLE_INJURIES = List.of(
            new Injury("Stłuczenie mięśnia uda", 3, 7),
            new Injury("Naciągnięcie mięśnia łydki", 5, 12),
            new Injury("Skręcenie stawu skokowego (I stopień)", 7, 14),
            new Injury("Rozcięcie głowy z szyciem", 4, 10),
            new Injury("Stłuczenie żebra", 7, 21),
            new Injury("Naciągnięcie pachwiny", 10, 21),

            new Injury("Naderwanie mięśnia dwugłowego", 21, 42),
            new Injury("Skręcenie kostki (II stopień)", 21, 45),
            new Injury("Uszkodzenie łąkotki", 30, 60),
            new Injury("Złamanie nosa", 14, 30),
            new Injury("Złamanie kości śródstopia", 42, 70),
            new Injury("Zwichnięcie barku", 30, 60),

            new Injury("Złamanie kości piszczelowej", 90, 150),
            new Injury("Złamanie kości strzałkowej", 60, 120),
            new Injury("Zerwanie ścięgna Achillesa", 120, 210),
            new Injury("Uszkodzenie więzadła pobocznego (MCL)", 60, 120),
            new Injury("Pęknięcie kości przedramienia", 45, 90),

            new Injury("Zerwanie więzadeł krzyżowych (ACL)", 180, 270),
            new Injury("Zerwanie więzadeł tylnych (PCL)", 150, 240),
            new Injury("Skomplikowane złamanie z przemieszczeniem", 210, 365)
    );

    public Injury getRandomInjury() {
        int index = ThreadLocalRandom.current().nextInt(POSSIBLE_INJURIES.size());
        return POSSIBLE_INJURIES.get(index);
    }

    public Injury getRandomInjury(RandomNumberGenerator random) {
        int index = random.getRandomInt(0, POSSIBLE_INJURIES.size() - 1);
        return POSSIBLE_INJURIES.get(index);
    }

    public List<Injury> getPossibleInjuries() {
        return POSSIBLE_INJURIES;
    }
}