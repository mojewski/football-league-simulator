package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.Player;

public class RetiredState implements PlayerState {

    @Override
    public boolean canPlay() { return false; }

    @Override
    public void passDay(Player player) {}

    @Override
    public void playMatch(Player player) {}
}

