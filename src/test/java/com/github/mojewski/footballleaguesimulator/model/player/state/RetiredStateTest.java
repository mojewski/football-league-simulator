package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.PlayerState;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.RetiredState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RetiredStateTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .build();
    }

    @Test
    public void ShouldNotAllowRetiredPlayerToPlay() {
        PlayerState retiredState = new RetiredState();

        assertFalse(retiredState.canPlay());
    }

    @Test
    public void shouldRemainInRetiredStateOnPassDayAndPlayMatch() {
        PlayerState retiredState = new RetiredState();
        player.setState(retiredState);

        player.getCurrentState().passDay(player);
        player.getCurrentState().playMatch(player);

        assertInstanceOf(RetiredState.class, player.getCurrentState());
        assertFalse(player.getCurrentState().canPlay());
    }
}