package com.github.mojewski.footballleaguesimulator.service.match.event;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.StoppageTime;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MatchEventGeneratorTest {

    @Mock
    private RandomNumberGenerator random;

    @Mock
    private Team team;

    @InjectMocks
    private MatchEventGenerator eventGenerator;

    private Player striker;
    private Player midfielder;
    private Player defender;

    @BeforeEach
    void setUp() {
        striker = createMockPlayer("Striker", Position.FORWARD, 80, 25, 90);
        midfielder = createMockPlayer("Midfielder", Position.MIDFIELDER, 75, 27, 85);
        defender = createMockPlayer("Defender", Position.DEFENDER, 70, 32, 80);
    }

    private Player createMockPlayer(String name, Position position, int overall, int age, int stamina) {
        Player player = mock(Player.class);
        lenient().when(player.getName()).thenReturn(name);
        lenient().when(player.getPosition()).thenReturn(position);
        lenient().when(player.getEffectiveOverall()).thenReturn(overall);
        lenient().when(player.getAge()).thenReturn(age);
        lenient().when(player.getStamina()).thenReturn(stamina);
        return player;
    }

    @Test
    void shouldGenerateFirstHalfMinute() {
        StoppageTime stoppageTime = new StoppageTime(2, 5);

        when(random.getRandomInt(1, 100)).thenReturn(20);
        when(random.getRandomInt(1, 45 + stoppageTime.firstHalf())).thenReturn(30);

        assertEquals(30, eventGenerator.generateEventMinute(stoppageTime));
    }

    @Test
    void shouldGenerateSecondHalfMinute() {
        StoppageTime stoppageTime = new StoppageTime(2, 5);

        when(random.getRandomInt(1, 100)).thenReturn(50);
        when(random.getRandomInt(46, 90 + stoppageTime.secondHalf())).thenReturn(60);

        assertEquals(60, eventGenerator.generateEventMinute(stoppageTime));
    }

    @Test
    void generateGoalEventWithAssist() {
        List<Player> activePlayers = List.of(striker, midfielder, defender);
        when(random.getRandomInt(1, 100)).thenReturn(30);

        MatchEvent event = eventGenerator.generateGoalEvent(team, 15, true, activePlayers);

        assertNotNull(event);
        assertEquals(15, event.minute());
        assertEquals(EventType.GOAL, event.type());
        assertEquals(team, event.team());
        assertNotNull(event.primaryPlayer());
        assertNotNull(event.secondaryPlayer());
        assertNotEquals(event.primaryPlayer(), event.secondaryPlayer());
        assertTrue(event.isHomeTeam());
    }

    @Test
    void generateGoalEventWithoutAssist() {
        List<Player> activePlayers = List.of(striker, midfielder, defender);
        when(random.getRandomInt(1, 100)).thenReturn(80);

        MatchEvent event = eventGenerator.generateGoalEvent(team, 60, false, activePlayers);

        assertNotNull(event);
        assertEquals(60, event.minute());
        assertEquals(EventType.GOAL, event.type());
        assertEquals(team, event.team());
        assertNotNull(event.primaryPlayer());
        assertNull(event.secondaryPlayer());
        assertFalse(event.isHomeTeam());
    }

    @Test
    void generateSubstitutionEventWithEmptyBench() {
        List<Player> activePlayers = List.of(striker, midfielder);
        List<Player> bench = Collections.emptyList();

        MatchEvent event = eventGenerator.generateSubstitutionEvent(team, 75, true, activePlayers, bench);

        assertNull(event);
    }

    @Test
    void generateSubstitutionEventForSamePositionFromBench() {
        Player tiredMidfielder = createMockPlayer("Tired Midfielder", Position.MIDFIELDER, 70, 28, 30);
        Player freshMidfielder = createMockPlayer("Fresh Midfielder", Position.MIDFIELDER, 78, 22, 95);

        List<Player> activePlayers = List.of(striker, tiredMidfielder, defender);
        List<Player> bench = List.of(freshMidfielder);

        MatchEvent event = eventGenerator.generateSubstitutionEvent(team, 65, true, activePlayers, bench);

        assertNotNull(event);
        assertEquals(EventType.SUBSTITUTION, event.type());
        assertEquals(tiredMidfielder, event.primaryPlayer());
        assertEquals(freshMidfielder, event.secondaryPlayer());
    }

    @Test
    void generateSubstitutionEventToBestBenchPlayer() {
        Player tiredForward = createMockPlayer("Tired Striker", Position.FORWARD, 70, 28, 20);
        Player benchDefenderLow = createMockPlayer("Worse Defender", Position.DEFENDER, 65, 20, 90);
        Player benchDefenderHigh = createMockPlayer("Better Defender", Position.DEFENDER, 75, 22, 90);

        List<Player> activePlayers = List.of(tiredForward, midfielder);
        List<Player> bench = List.of(benchDefenderLow, benchDefenderHigh);

        MatchEvent event = eventGenerator.generateSubstitutionEvent(team, 70, false, activePlayers, bench);

        assertNotNull(event);
        assertEquals(tiredForward, event.primaryPlayer());
        assertEquals(benchDefenderHigh, event.secondaryPlayer());
    }
}
