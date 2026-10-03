package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LeagueLeaderboardsTest {
    private League league;
    private LeagueLeaderboards leaderboards;

    @BeforeEach
    void setUp() {
        this.league = new League("LaTest");

        for(int i = 0; i < 18; i++) {
            league.addTeam(createTeam("FC Test" + i, 1));
        }

        this.leaderboards = new LeagueLeaderboards(league);
    }

    @Test
    void shouldGenerateLeaderboardsWithCorrectOrder() {
        leaderboards.setLimit(3);

        Player player1 = league.getTeams().getFirst().getPlayerList().getFirst();
        Player player2 = league.getTeams().getFirst().getPlayerList().getLast();
        Player player3 = league.getTeams().getFirst().getPlayerList().get(4);

        player1.getStats().addGoals(20);
        player2.getStats().addGoals(40);
        player3.getStats().addGoals(2);

        assertThat(leaderboards.getTopPlayers(LeaderboardCriterion.GOALS)).containsExactly(player2, player1, player3);

        player1.getStats().addMinutesPlayed(90);
        player2.getStats().addMinutesPlayed(91);
        player3.getStats().addMinutesPlayed(100);

        assertThat(leaderboards.getTopPlayers(LeaderboardCriterion.MINUTES)).containsExactly(player3, player2, player1);

        player1.getStats().addMatchesPlayed();
        player1.getStats().addMatchesPlayed();

        player2.getStats().addMatchesPlayed();

        player3.getStats().addMatchesPlayed();
        player3.getStats().addMatchesPlayed();
        player3.getStats().addMatchesPlayed();

        assertThat(leaderboards.getTopPlayers(LeaderboardCriterion.MINUTES_PER_MATCH)).containsExactly(player2, player1, player3);

        player1.getStats().recordMatchPerformance(4.6);
        player2.getStats().recordMatchPerformance(7.6);
        player3.getStats().recordMatchPerformance(1.6);

        assertThat(leaderboards.getTopPlayers(LeaderboardCriterion.AVERAGE_RATING)).containsExactly(player2, player1, player3);

    }

    private Team createTeam(String name, int skillLevel) {
        Team team = new Team(name, 1, 1, 1, Formation.F_4_3_3);
        PlayerAttributes attributes = createAttributes(skillLevel);

        for (Position position : Position.values()) {
            for (int i = 0; i < 5; i++) {
                Player player = new PlayerBuilder()
                        .setPosition(position)
                        .setAttributes(attributes)
                        .build();
                team.addPlayer(player);
            }
        }
        return team;
    }

    private PlayerAttributes createAttributes(int value) {
        return new PlayerAttributes(value, value, value, value, value, value, value);
    }}
