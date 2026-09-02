package com.github.mojewski.footballleaguesimulator.model.player.state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public interface PlayerState {

    boolean canPlay();

    void passDay(Player player);

    void playMatch(Player player);
}
