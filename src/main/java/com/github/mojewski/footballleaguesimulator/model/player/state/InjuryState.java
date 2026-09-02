package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Injury;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.service.RandomNumberGenerator;

public class InjuryState implements PlayerState {

    private final Injury injury;
    private int daysRemaining;
    private final int initialDays;
    private boolean penaltyApplied;
    private int dropAmount;
    private final RandomNumberGenerator random;

    public InjuryState(Injury injury) {
        this(injury, new RandomNumberGenerator());
    }

    public InjuryState(Injury injury, RandomNumberGenerator random) {
        this.injury = injury;
        this.random = random;
        this.initialDays = Math.max(1, injury.daysOut());
        this.daysRemaining = initialDays;
    }

    @Override
    public boolean canPlay() { return false; }

    @Override
    public void passDay(Player player) {
        daysRemaining--;
        player.getStats().addDayInjured();

        if (player.getStamina() > 30) {
            player.setStamina(30);
        }

        if (initialDays >= 60 && !penaltyApplied) {
            applySkillDrop(player);
            penaltyApplied = true;
        }

        if (daysRemaining <= 0) {
            int unfitDays = initialDays / 5;
            if (unfitDays > 0) {
                player.setState(new UnfitState(unfitDays));
            } else {
                player.setState(new AvailableState());
            }
        }
    }

    @Override
    public void playMatch(Player player) {}

    private void applySkillDrop(Player player) {
        dropAmount = (initialDays / 60) * 4;
        if (player.getAttributes() != null) {
            player.getAttributes().decreaseSkills(dropAmount, player.getPosition(), random);
            player.recalculateOverall();
        }
    }

    public int getDaysRemaining() { return daysRemaining; }
    public int getDropAmount() { return dropAmount; }
    public boolean isPenaltyApplied() { return penaltyApplied; }
    public Injury getInjury() { return injury; }
}