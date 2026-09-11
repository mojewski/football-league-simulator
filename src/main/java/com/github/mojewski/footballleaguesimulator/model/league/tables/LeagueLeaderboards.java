package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.List;

public class LeagueLeaderboards {
    private int limit;

    private List<Team> teams;

    public LeagueLeaderboards(League league) {
        this.teams = league.getTeams();
        this.limit = 10;
    }

    public void setLimit(int count) {
        if(count <= 0 || count >= 50) {
            return;
        }
        this.limit = count;
    }

    public List<Player> getTopPlayers(LeaderboardCriterion criterion) {
        return getTopPlayers(criterion, this.limit);
    }

    public List<Player> getTopPlayers(LeaderboardCriterion criterion, int limit) {
        return teams.stream()
                .flatMap(team -> team.getPlayerList().stream())
                .sorted(criterion.getComparator())
                .limit(limit)
                .toList();
    }
}
