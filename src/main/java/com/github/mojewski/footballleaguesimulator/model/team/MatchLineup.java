package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;

import java.util.List;

public class MatchLineup {

    private final Formation formation;
    private final List<Player> startingEleven;
    private final List<Player> bench;

    public MatchLineup(Formation formation, List<Player> startingEleven, List<Player> bench) {
        this.formation = formation;
        this.startingEleven = startingEleven;
        this.bench = bench;
    }

    public List<Player> getStartingPlayersForPosition(Position position) {
        return startingEleven.stream()
                .filter(player -> player.getPosition() == position)
                .toList();
    }

    public Formation getFormation() { return formation; }
    public List<Player> getStartingEleven() { return startingEleven; }
    public List<Player> getBench() { return bench; }
}