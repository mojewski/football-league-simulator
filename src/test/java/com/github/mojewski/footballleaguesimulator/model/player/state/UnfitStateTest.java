package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.AvailableState;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.PlayerState;
import com.github.mojewski.footballleaguesimulator.model.player.player_state.UnfitState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnfitStateTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .build();
    }

    @Test
    public void ShouldAllowUnfitPlayerToPlay() {
        PlayerState unfitState = new UnfitState(7);

        assertTrue(unfitState.canPlay());
    }

    @Test
    void shouldCalculateRecommendedMinutesCorrectly() {
        UnfitState unfitState = new UnfitState(10);
        player.setState(unfitState);

        assertEquals(15, unfitState.getMaxRecommendedMinutes());

        for (int i = 0; i < 5; i++) {
            player.getCurrentState().passDay(player);
        }

        assertEquals(52, unfitState.getMaxRecommendedMinutes());
    }

    @Test
    public void shouldReduceDaysRemainingAndChangeStateToAvailable() {
        UnfitState unfitState = new UnfitState(2);
        player.setState(unfitState);

        player.getCurrentState().passDay(player);
        assertEquals(1, unfitState.getDaysRemaining());
        assertInstanceOf(UnfitState.class, player.getCurrentState());

        player.getCurrentState().passDay(player);
        assertEquals(0, unfitState.getDaysRemaining());
        assertInstanceOf(AvailableState.class, player.getCurrentState());
    }
}
