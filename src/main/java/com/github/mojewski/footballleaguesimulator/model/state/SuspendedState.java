package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.Player;

public class SuspendedState implements PlayerState {

    private int matchesOff;

    public SuspendedState(int matchesOff) {
        this.matchesOff = matchesOff;
    }

    @Override
    public boolean canPlay() { return false; }

    @Override
    public void passDay(Player player) {
        if (matchesOff <= 0) {
            player.setState(new AvailableState());
        }
    }

    @Override
    public void playMatch(Player player) {
        matchesOff--;
    }

    public int getMatchesOff() {
        return matchesOff;
    }
}

