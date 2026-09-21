package com.github.mojewski.footballleaguesimulator.service.league;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.league.schedule.Matchday;
import com.github.mojewski.footballleaguesimulator.model.match.Match;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleGeneratorTest {

    League league;
    ScheduleGenerator generator;

    @BeforeEach
    void setUp() {
        league = new League("Test League");
        for(int i = 0; i < 18; i++) {
            league.addTeam(new Team("FC Test " + i));
        }
        generator = new ScheduleGenerator(league);
    }

    @Test
    void shouldGenerateScheduleWithCorrectAmountOfMatches() {
        List<Matchday> schedule = generator.generateFullSchedule();

        int totalMatchdays = schedule.size();
        int totalMatchesInMatchday = schedule.getFirst().matches().size();
        int totalMatches = schedule.stream()
                .mapToInt(matchday -> matchday.matches().size())
                .sum();

        int expectedNumOfMatchdays = (league.getTeams().size() - 1) * 2;
        int expectedNumOfMatchesInMatchday = league.getTeams().size() / 2;
        int expectedNumOfMatches = expectedNumOfMatchdays * (league.getTeams().size() / 2);

        assertFalse(schedule.isEmpty());
        assertEquals(expectedNumOfMatchdays, totalMatchdays);
        assertEquals(expectedNumOfMatchesInMatchday, totalMatchesInMatchday);
        assertEquals(expectedNumOfMatches, totalMatches);
    }

    @Test
    void shouldGenerateCorrectScheduleForOddNumberOfTeams() {
        League oddLeague = new League("Odd League");
        for (int i = 0; i < 17; i++) {
            oddLeague.addTeam(new Team("FC Test " + i));
        }
        ScheduleGenerator oddGenerator = new ScheduleGenerator(oddLeague);

        List<Matchday> schedule = oddGenerator.generateFullSchedule();

        boolean hasPauseMatch = schedule.stream()
                .flatMap(matchday -> matchday.matches().stream())
                .anyMatch(match -> match.getHomeTeam().getName().equals("pause")
                        || match.getAwayTeam().getName().equals("pause"));

        assertFalse(hasPauseMatch);

        assertEquals(34, schedule.size());

        int matchesPerMatchday = schedule.getFirst().matches().size();
        assertEquals(8, matchesPerMatchday);
    }

    @Test
    void shouldGenerateSecondRoundCorrectly() {
        List<Matchday> schedule = generator.generateFullSchedule();
        int halfRounds = schedule.size() / 2;

        for (int i = 0; i < halfRounds; i++) {
            List<Match> firstRoundMatches = schedule.get(i).matches();
            List<Match> secondRoundMatches = schedule.get(i + halfRounds).matches();

            for (int j = 0; j < firstRoundMatches.size(); j++) {
                Match original = firstRoundMatches.get(j);
                Match rematch = secondRoundMatches.get(j);

                assertEquals(original.getHomeTeam(), rematch.getAwayTeam());
                assertEquals(original.getAwayTeam(), rematch.getHomeTeam());
            }
        }
    }
}
