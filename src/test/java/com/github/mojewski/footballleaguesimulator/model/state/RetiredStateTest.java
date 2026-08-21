package com.github.mojewski.footballleaguesimulator.model.state;

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
                .getResult();
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

        retiredState.passDay(player);
        retiredState.playMatch(player);

        assertInstanceOf(RetiredState.class, player.getCurrentState());
        assertFalse(player.getCurrentState().canPlay());
    }
}
