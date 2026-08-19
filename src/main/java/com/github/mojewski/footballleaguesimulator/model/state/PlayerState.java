package com.github.mojewski.footballleaguesimulator.model.state;

import com.github.mojewski.footballleaguesimulator.model.Player;

public interface PlayerState {

    boolean canPlay();

    void passDay(Player player);

    void playMatch(Player player);
}
