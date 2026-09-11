package com.github.mojewski.footballleaguesimulator.model.player;

import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class FreeAgents {

    private final RandomNumberGenerator random;
    private final List<Player> freeAgents = new ArrayList<>();

    public FreeAgents(RandomNumberGenerator random) {
        this.random = random;
    }

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