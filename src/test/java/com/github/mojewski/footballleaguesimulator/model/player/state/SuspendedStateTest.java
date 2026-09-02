package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuspendedStateTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .build();
    }

    @Test
    void shouldNotAllowSuspendedPlayerToPlay() {
        PlayerState suspendedState = new SuspendedState(1);
        assertFalse(suspendedState.canPlay());
    }

    @Test
    void shouldReduceDaysOffCorrectly() {
        SuspendedState suspendedState = new SuspendedState(5);
        player.setState(suspendedState);

        player.getCurrentState().playMatch(player);
        assertEquals(4, suspendedState.getMatchesOff());
    }

    @Test
    void shouldChangeStateToAvailable() {
        SuspendedState suspendedState = new SuspendedState(5);
        player.setState(suspendedState);

        while (suspendedState.getMatchesOff() > 0) {
            player.getCurrentState().playMatch(player);
        }

        player.getCurrentState().passDay(player);

        assertInstanceOf(AvailableState.class, player.getCurrentState());
        assertTrue(player.getCurrentState().canPlay());
    }
}