package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public class AvailableState implements PlayerState {

    @Override
    public boolean canPlay() { return true; }

    @Override
    public void passDay(Player player) {}

    @Override
    public void playMatch(Player player) {}
}

