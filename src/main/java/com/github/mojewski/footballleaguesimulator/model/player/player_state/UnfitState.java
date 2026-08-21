package com.github.mojewski.footballleaguesimulator.model.player.player_state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public class UnfitState implements PlayerState {

    private final int initialDays;
    private int daysRemaining;

    public UnfitState(int daysUnfit) {
        this.initialDays = Math.max(1, daysUnfit);
        this.daysRemaining = this.initialDays;
    }

    @Override
    public boolean canPlay() { return true; }

    @Override
    public void passDay(Player player) {
        daysRemaining--;

        if (daysRemaining <= 0) {
            player.setState(new AvailableState());
        }
    }

    @Override
    public void playMatch(Player player) {}

    public int getMaxRecommendedMinutes() {
        double recoveryProgress = 1.0 - ((double) daysRemaining / initialDays);
        int minMinutes = 15;
        int maxMinutes = 90;

        return minMinutes + (int) (recoveryProgress * (maxMinutes - minMinutes));
    }

    public int getDaysRemaining() { return daysRemaining; }
}