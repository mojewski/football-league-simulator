package com.github.mojewski.footballleaguesimulator.service.match;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.match.MatchStats;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchRatingGeneratorTest {

    @Mock
    private RandomNumberGenerator random;

    @Mock
    private Team team;

    @Mock
    private MatchStats stats;

    @InjectMocks
    private MatchRatingGenerator ratingGenerator;

    private Player striker;
    private Player midfielder;
    private Player defender;
    private Player goalkeeper;

    @BeforeEach
    void setUp() {
        striker = createMockPlayer("Striker", Position.FORWARD, 80, 25, 90);
        midfielder = createMockPlayer("Midfielder", Position.MIDFIELDER, 75, 27, 85);
        defender = createMockPlayer("Defender", Position.DEFENDER, 70, 32, 80);
        goalkeeper = createMockPlayer("Keeper", Position.GOALKEEPER, 70, 34, 99);
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
    void shouldGenerateRatingInBounds() {
        when(random.getRandomDouble(-1.0, 1.0)).thenReturn(0.0);
        when(stats.getHomeGoals()).thenReturn(40);
        when(stats.getAwayGoals()).thenReturn(0);
        when(stats.getEvents()).thenReturn(List.of());

        assertEquals(1.0, ratingGenerator.generateAwayDefenderRating(stats, defender));
        assertEquals(10.0, ratingGenerator.generateHomeAttackerRating(stats, striker));
    }

    @Test
    void shouldAddBonusCorrectly() {
        when(random.getRandomDouble(-1.0, 1.0)).thenReturn(0.0);

        when(stats.getHomeGoals()).thenReturn(1);
        when(stats.getAwayGoals()).thenReturn(0);
        when(stats.getEvents()).thenReturn(List.of());

        double goalkeeperRating = ratingGenerator.generateHomeGoalkeeperRating(stats, goalkeeper);
        double defenderRating = ratingGenerator.generateHomeDefenderRating(stats, defender);
        double midfielderRating = ratingGenerator.generateHomeMidfielderRating(stats, midfielder);
        double attackerRating = ratingGenerator.generateHomeAttackerRating(stats, striker);

        assertEquals(6.2, goalkeeperRating);
        assertEquals(6.4, defenderRating);
        assertEquals(6.6, midfielderRating);
        assertEquals(6.8, attackerRating);
    }

    @Test
    void shouldAddCardBonusCorrectly() {
        when(random.getRandomDouble(-1.0, 1.0)).thenReturn(0.0);
        when(stats.getHomeGoals()).thenReturn(1);
        when(stats.getAwayGoals()).thenReturn(0);

        MatchEvent yellowCardEvent = new MatchEvent(20, EventType.YELLOW_CARD, team, midfielder, null, true);
        when(stats.getEvents()).thenReturn(List.of(yellowCardEvent));

        double midfielderWithYellow = ratingGenerator.generateHomeMidfielderRating(stats, midfielder);
        assertEquals(6.3, midfielderWithYellow);

        MatchEvent strikerYellow1 = new MatchEvent(10, EventType.YELLOW_CARD, team, striker, null, true);
        MatchEvent strikerYellow2 = new MatchEvent(50, EventType.YELLOW_CARD, team, striker, null, true);
        when(stats.getEvents()).thenReturn(List.of(strikerYellow1, strikerYellow2));

        double strikerWithTwoYellows = ratingGenerator.generateHomeAttackerRating(stats, striker);
        assertEquals(5.3, strikerWithTwoYellows);
    }
}