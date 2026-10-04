package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.MatchLineup;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.match.event.MatchEventGenerator;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchSimulatorTest {

    @Mock
    private MatchEventGenerator eventGenerator;
    @Mock
    private MatchStatsGenerator statsGenerator;
    @Mock
    private RandomNumberGenerator random;

    @InjectMocks
    private MatchSimulator matchSimulator;

    private Team team;
    private Player starter1;
    private Player bench1;

    @BeforeEach
    void setUp() {
        team = mock(Team.class);
        MatchLineup lineup = mock(MatchLineup.class);

        starter1 = mock(Player.class);
        bench1 = mock(Player.class);

        lenient().when(team.getActiveLineup()).thenReturn(lineup);
        lenient().when(lineup.getStartingEleven()).thenReturn(List.of(starter1));
        lenient().when(lineup.getBench()).thenReturn(List.of(bench1));
    }

    @Test
    void shouldRemovePlayerFromActiveAfterRedCard() {
        MatchEvent redCard = new MatchEvent(30, EventType.RED_CARD, team, starter1, null, true);
        List<MatchEvent> events = List.of(redCard);

        List<Player> activeBefore = matchSimulator.getActivePlayersForMinute(25, team, List.of(starter1), events);
        List<Player> activeAfter = matchSimulator.getActivePlayersForMinute(35, team, List.of(starter1), events);

        assertThat(activeBefore).contains(starter1);
        assertThat(activeAfter).doesNotContain(starter1);
    }

    @Test
    void shouldReplacePlayersAfterSubstitution() {
        MatchEvent sub = new MatchEvent(60, EventType.SUBSTITUTION, team, starter1, bench1, true);
        List<MatchEvent> events = List.of(sub);

        List<Player> activeAfter = matchSimulator.getActivePlayersForMinute(65, team, List.of(starter1), events);

        assertThat(activeAfter).contains(bench1);
        assertThat(activeAfter).doesNotContain(starter1);
    }

    @Test
    void newPlayerShouldBeActiveAfterSubstitution() {
        MatchEvent sub = new MatchEvent(60, EventType.SUBSTITUTION, team, starter1, bench1, true);

        List<Player> activePlayers = matchSimulator.getActivePlayersForMinute(70, team, List.of(starter1), List.of(sub));

        assertThat(activePlayers).contains(bench1);
    }

    @Test
    void shouldGenerateSubstitutionWhenPlayerIsInjured() {
        StoppageTime stoppage = new StoppageTime(1, 3);

        when(random.getRandomInt(0, 5)).thenReturn(0);
        when(random.getRandomInt(1, 100)).thenReturn(1);
        when(random.getRandomInt(3, 5)).thenReturn(0);

        MatchEvent injuryEvent = new MatchEvent(40, EventType.INJURY, team, starter1, null, true);
        MatchEvent subEvent = new MatchEvent(40, EventType.SUBSTITUTION, team, starter1, bench1, true);

        when(eventGenerator.generateEventMinute(stoppage)).thenReturn(40);
        when(eventGenerator.generateInjuryEvent(eq(team), eq(40), eq(true), anyList()))
                .thenReturn(injuryEvent);
        when(eventGenerator.generateSubstitutionEvent(eq(team), eq(40), eq(true), anyList(), anyList()))
                .thenReturn(subEvent);

        List<MatchEvent> events = matchSimulator.simulateMatchEvents(0, 0, team, team, stoppage);

        assertThat(events).contains(injuryEvent, subEvent);
    }
}