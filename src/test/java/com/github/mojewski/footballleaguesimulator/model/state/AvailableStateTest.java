package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.PlayerState;
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
                .getResult();
    }

    @Test
    public void ShouldAllowAvailablePlayerToPlay() {
        PlayerState availableState = new AvailableState();

        assertTrue(availableState.canPlay());
    }

    @Test
    public void shouldRemainInAvailableStateOnPassDayAndPlayMatch() {
        PlayerState availableState = new AvailableState();
        player.setState(availableState);

        availableState.passDay(player);
        availableState.playMatch(player);

        assertInstanceOf(AvailableState.class, player.getCurrentState());
        assertTrue(player.getCurrentState().canPlay());
    }
}
