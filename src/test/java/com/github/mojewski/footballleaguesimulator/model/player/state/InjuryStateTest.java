package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjuryStateTest {

    private Player player;
    private Injury injury;

    @BeforeEach
    void setUp() {
        injury = new Injury("Example of injury", 180, 180);
        player = new PlayerBuilder()
                .setFirstName("Robert")
                .setLastName("Lewandowski")
                .setPosition(Position.FORWARD)
                .setAttributes(new PlayerAttributes(90, 40, 75, 85, 88, 80, 92))
                .setStats(new PlayerStats())
                .build();
    }

    @Test
    void shouldNotAllowInjuredPlayerToPlay() {
        PlayerState injuryState = new InjuryState(injury);
        assertFalse(injuryState.canPlay());
    }

    @Test
    void shouldApplyValidSkillDrop() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        int initialOverall = player.getOverall();

        player.getCurrentState().passDay(player);

        assertEquals(12, injuryState.getDropAmount());
        assertTrue(player.getOverall() < initialOverall);
        assertTrue(injuryState.isPenaltyApplied());
    }

    @Test
    void shouldReduceDaysOffCorrectly() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        player.getCurrentState().passDay(player);
        assertEquals(179, injuryState.getDaysRemaining());

        while (injuryState.getDaysRemaining() > 0) {
            player.getCurrentState().passDay(player);
        }

        assertEquals(0, injuryState.getDaysRemaining());
    }

    @Test
    void shouldChangeStateToUnfitState() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        while (injuryState.getDaysRemaining() > 0) {
            player.getCurrentState().passDay(player);
        }

        assertInstanceOf(UnfitState.class, player.getCurrentState());

        UnfitState unfitState = (UnfitState) player.getCurrentState();
        assertEquals(36, unfitState.getDaysRemaining());
    }

    @Test
    void shouldChangeStateToAvailableState() {
        Injury shortInjury = new Injury("Example of injury", 2, 2);
        InjuryState injuryState = new InjuryState(shortInjury);
        player.setState(injuryState);

        while (injuryState.getDaysRemaining() > 0) {
            player.getCurrentState().passDay(player);
        }

        assertInstanceOf(AvailableState.class, player.getCurrentState());
    }

    @Test
    void shouldNotApplySkillDropForShortInjury() {
        Injury shortInjury = new Injury("Short injury", 2, 2);
        InjuryState injuryState = new InjuryState(shortInjury);
        player.setState(injuryState);

        int initialOverall = player.getOverall();

        player.getCurrentState().passDay(player);

        assertEquals(0, injuryState.getDropAmount());
        assertEquals(initialOverall, player.getOverall());
        assertFalse(injuryState.isPenaltyApplied());
    }

    @Test
    void shouldCapStaminaAtThirtyDuringInjury() {
        player.setStamina(90);
        PlayerState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        player.getCurrentState().passDay(player);

        assertEquals(30, player.getStamina());
    }

    @Test
    void shouldIncrementDaysInjuredInPlayerStats() {
        PlayerState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        int initialDaysInjured = player.getStats().getDaysInjured();

        player.getCurrentState().passDay(player);

        assertEquals(initialDaysInjured + 1, player.getStats().getDaysInjured());
    }
}