package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.*;
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
                .setPotential(90)
                .setRating(89)
                .setIsRetired(false)
                .getResult();
    }

    @Test
    public void ShouldNotAllowInjuriedPlayerToPlay() {
        PlayerState injuryState = new InjuryState(injury);

        assertFalse(injuryState.canPlay());
    }

    @Test
    public void ShouldApplyValidSkillDrop() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        injuryState.passDay(player);

        assertEquals(6, injuryState.getDropAmount());
        assertEquals(83, player.getRating());
        assertTrue(injuryState.isPenaltyApplied());
    }

    @Test
    public void ShouldReduceDaysOffCorrectly() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        injuryState.passDay(player);
        assertEquals(179, injuryState.getDaysRemaining());

        while(injuryState.getDaysRemaining() > 0) {
            injuryState.passDay(player);
        }

        assertEquals(0, injuryState.getDaysRemaining());
    }

    @Test
    public void ShouldChangeStateToUnfitState() {
        InjuryState injuryState = new InjuryState(injury);
        player.setState(injuryState);

        while (injuryState.getDaysRemaining() > 0) {
            injuryState.passDay(player);
        }

        assertInstanceOf(UnfitState.class, player.getCurrentState());

        UnfitState unfitState = (UnfitState) player.getCurrentState();
        assertEquals(36, unfitState.getDaysRemaining());
    }

    @Test
    public void ShouldChangeStateToAvailableState() {
        Injury shortInjury = new Injury("Example of injury", 2, 2);
        InjuryState injuryState = new InjuryState(shortInjury);
        player.setState(injuryState);

        while(injuryState.getDaysRemaining() > 0) {
            injuryState.passDay(player);
        }

        assertInstanceOf(AvailableState.class, player.getCurrentState());
    }

    @Test
    public void ShouldNotApplySkillDropForShortInjury() {
        Injury shortInjury = new Injury("Short injury", 2, 2);
        InjuryState injuryState = new InjuryState(shortInjury);
        player.setState(injuryState);

        injuryState.passDay(player);

        assertEquals(0, injuryState.getDropAmount());
        assertEquals(89, player.getRating());
        assertFalse(injuryState.isPenaltyApplied());
    }
}
