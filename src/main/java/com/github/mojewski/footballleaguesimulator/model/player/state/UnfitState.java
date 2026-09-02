package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public class UnfitState implements PlayerState {

    private final int initialDays;
    private int daysRemaining;

    public UnfitState(int daysUnfit) {
        this.initialDays = Math.max(1, daysUnfit);
        this.daysRemaining = this.initialDays;
    }

    @Override
    public boolean canPlay() {
        return true;
    }

    @Override
    public void passDay(Player player) {
        daysRemaining--;

        player.setStamina(calculateTargetStamina());

        if (daysRemaining <= 0) {
            player.setState(new AvailableState());
        }
    }

    @Override
    public void playMatch(Player player) {
        player.regenerateStamina(10);
        // TODO: zrobic inaczej logike wplywu meczu na kondycje
    }

    public int getMaxRecommendedMinutes() {
        double recoveryProgress = 1.0 - ((double) daysRemaining / initialDays);
        int minMinutes = 15;
        int maxMinutes = 90;

        return minMinutes + (int) Math.round(recoveryProgress * (maxMinutes - minMinutes));
    }

    private int calculateTargetStamina() {
        double progress = (double) (initialDays - daysRemaining) / initialDays;
        return 30 + (int) Math.round(progress * 69);
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }
}