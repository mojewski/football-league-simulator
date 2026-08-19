package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.Player;
import com.github.mojewski.footballleaguesimulator.model.PlayerBuilder;
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
                .getResult();
    }

    @Test
    public void ShouldNotAllowSuspendedPlayerToPlay() {
        PlayerState suspendedState = new SuspendedState(1);

        assertFalse(suspendedState.canPlay());
    }

    @Test
    public void ShouldReduceDaysOffCorrectly() {
        SuspendedState suspendedState = new SuspendedState(5);
        player.setState(suspendedState);

        suspendedState.playMatch(player);
        assertEquals(4, suspendedState.getMatchesOff());
    }

    @Test
    public void ShouldChangeStateToAvailable() {
        SuspendedState suspendedState = new SuspendedState(5);
        player.setState(suspendedState);

        while(suspendedState.getMatchesOff() > 0) {
            suspendedState.playMatch(player);
        }

        suspendedState.passDay(player);

        assertInstanceOf(AvailableState.class, player.getCurrentState());
    }
}
