package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
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
    void shouldAllowUnfitPlayerToPlay() {
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

        assertEquals(53, unfitState.getMaxRecommendedMinutes());
    }

    @Test
    void shouldReduceDaysRemainingAndChangeStateToAvailable() {
        UnfitState unfitState = new UnfitState(2);
        player.setState(unfitState);

        player.getCurrentState().passDay(player);
        assertEquals(1, unfitState.getDaysRemaining());
        assertInstanceOf(UnfitState.class, player.getCurrentState());

        player.getCurrentState().passDay(player);
        assertEquals(0, unfitState.getDaysRemaining());
        assertInstanceOf(AvailableState.class, player.getCurrentState());
    }

    @Test
    void shouldCalculateTargetStaminaCorrectlyOverTime() {
        player.setStamina(30);

        int initialDays = 10;
        UnfitState unfitState = new UnfitState(initialDays);
        player.setState(unfitState);

        unfitState.passDay(player);
        assertEquals(37, player.getStamina());
        assertEquals(9, unfitState.getDaysRemaining());

        for (int i = 0; i < 4; i++) {
            unfitState.passDay(player);
        }
        assertEquals(65, player.getStamina());
        assertEquals(5, unfitState.getDaysRemaining());

        for (int i = 0; i < 5; i++) {
            unfitState.passDay(player);
        }

        assertEquals(99, player.getStamina());
        assertEquals(0, unfitState.getDaysRemaining());
        assertTrue(player.getCurrentState() instanceof AvailableState);
    }
}