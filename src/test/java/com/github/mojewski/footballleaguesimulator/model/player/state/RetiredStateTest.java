package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
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
    void shouldNotAllowRetiredPlayerToPlay() {
        PlayerState retiredState = new RetiredState();

        assertFalse(retiredState.canPlay());
    }

    @Test
    void shouldRemainInRetiredStateOnPassDayAndPlayMatch() {
        PlayerState retiredState = new RetiredState();
        player.setState(retiredState);

        player.getCurrentState().passDay(player);
        player.getCurrentState().playMatch(player);

        assertInstanceOf(RetiredState.class, player.getCurrentState());
        assertFalse(player.getCurrentState().canPlay());
    }
}