package com.github.mojewski.footballleaguesimulator.model.player;

import java.util.ArrayList;
import java.util.List;

public class FreeAgents {

    private final List<Player> freeAgents = new ArrayList<>();

    public void addFreeAgent(Player player) {
        if (player != null && !freeAgents.contains(player)) {
            freeAgents.add(player);
        }
    }

    public void removeFreeAgent(Player player) {
        freeAgents.remove(player);
    }

    public List<Player> getFreeAgents() {
        return freeAgents;
    }
}