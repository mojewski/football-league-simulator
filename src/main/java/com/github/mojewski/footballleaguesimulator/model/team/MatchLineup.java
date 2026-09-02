package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MatchLineup {

    private static final int STARTING_ELEVEN_SIZE = 11;
    private static final int BENCH_SIZE = 10;

    private final Formation formation;
    private final List<Player> startingEleven;
    private final List<Player> bench;

    public MatchLineup(Team team) {
        this.formation = team.getFormation();
        List<Player> readyPlayers = getReadyPlayers(team.getPlayerList());
        this.startingEleven = buildStartingEleven(readyPlayers);
        this.bench = buildBench(readyPlayers);
    }

    private List<Player> getReadyPlayers(List<Player> playerList) {
        return playerList.stream()
                .filter(player -> player.getCurrentState().canPlay())
                .toList();
    }

    private List<Player> buildStartingEleven(List<Player> readyPlayers) {
        return LineupUtils.buildLineupForFormation(
                readyPlayers,
                formation,
                Comparator.comparingInt(Player::getEffectiveOverall).reversed()
        );
    }

    private List<Player> buildBench(List<Player> readyPlayers) {
        return readyPlayers.stream()
                .filter(player -> !startingEleven.contains(player))
                .sorted(Comparator.comparingInt(Player::getEffectiveOverall).reversed())
                .limit(BENCH_SIZE)
                .toList();
    }

    public Formation getFormation() { return formation; }
    public List<Player> getStartingEleven() { return startingEleven; }
    public List<Player> getBench() { return bench; }
}