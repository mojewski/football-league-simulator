package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.LineupUtils;
import com.github.mojewski.footballleaguesimulator.model.team.MatchLineup;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.Comparator;
import java.util.List;

public class LineupGenerator {

    private static final int BENCH_SIZE = 10;

    public MatchLineup generateAutoLineup(Team team) {
        Formation formation = team.getFormation();
        List<Player> readyPlayers = getReadyPlayers(team.getPlayerList());

        List<Player> startingEleven = buildStartingEleven(readyPlayers, formation);
        List<Player> bench = buildBench(readyPlayers, startingEleven);

        return new MatchLineup(formation, startingEleven, bench);
    }

    private List<Player> getReadyPlayers(List<Player> playerList) {
        return playerList.stream()
                .filter(player -> player.getCurrentState() != null && player.getCurrentState().canPlay())
                .toList();
    }

    private List<Player> buildStartingEleven(List<Player> readyPlayers, Formation formation) {
        return LineupUtils.buildLineupForFormation(
                readyPlayers,
                formation,
                Comparator.comparingInt(Player::getEffectiveOverall).reversed()
        );
    }

    private List<Player> buildBench(List<Player> readyPlayers, List<Player> startingEleven) {
        return readyPlayers.stream()
                .filter(player -> !startingEleven.contains(player))
                .sorted(Comparator.comparingInt(Player::getEffectiveOverall).reversed())
                .limit(BENCH_SIZE)
                .toList();
    }
}