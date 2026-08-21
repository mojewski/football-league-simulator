package com.github.mojewski.footballleaguesimulator.model.player.player_state;

import com.github.mojewski.footballleaguesimulator.model.player.Player;

public interface PlayerState {

    boolean canPlay();

    void passDay(Player player);

    void playMatch(Player player);
}
