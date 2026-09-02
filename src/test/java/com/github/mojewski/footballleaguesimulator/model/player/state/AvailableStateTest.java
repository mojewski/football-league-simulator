package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableStateTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .build();
    }

    @Test
    void shouldAllowAvailablePlayerToPlay() {
        PlayerState availableState = new AvailableState();

        assertTrue(availableState.canPlay());
    }

    @Test
    void shouldRemainInAvailableStateOnPassDayAndPlayMatch() {
        PlayerState availableState = new AvailableState();
        player.setState(availableState);

        player.getCurrentState().passDay(player);
        player.getCurrentState().playMatch(player);

        assertInstanceOf(AvailableState.class, player.getCurrentState());
        assertTrue(player.getCurrentState().canPlay());
    }
}