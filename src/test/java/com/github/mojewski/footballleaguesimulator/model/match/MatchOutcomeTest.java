package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;
import com.github.mojewski.footballleaguesimulator.model.team.MatchLineup;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.team.LineupGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchOutcomeTest {
    private Match match;
    private LineupGenerator generator = new LineupGenerator();

    @BeforeEach
    void setUp() {
        Team strongTeam = createTeamWithSkill("FC Better Team", 99);
        Team weakTeam = createTeamWithSkill("FC Worse Team", 5);

        MatchLineup strongLineup = generator.generateAutoLineup(strongTeam);
        strongTeam.setActiveLineup(strongLineup);

        MatchLineup weakLineup = generator.generateAutoLineup(strongTeam);
        weakTeam.setActiveLineup(weakLineup);

        this.match = new Match(strongTeam, weakTeam);
    }

    @Test
    void shouldCalculateHigherXGForBetterTeam() {
        MatchOutcome outcome = new MatchOutcome(match);

        assertTrue(outcome.getHomeXG() > outcome.getAwayXG());
        assertTrue(outcome.getHomeXG() > 0);
        assertTrue(outcome.getAwayXG() >= 0);
    }

    private Team createTeamWithSkill(String name, int skillLevel) {
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
    }
}