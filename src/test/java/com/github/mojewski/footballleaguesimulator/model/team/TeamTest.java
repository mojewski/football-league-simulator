package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerAttributes;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerBuilder;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    void shouldCalculateTeamRatingCorrectly() {
        Team team = new Team("FC Test", 1_000, 50, 4, Formation.F_3_5_2);

        for (int i = 0; i < 3; i++) {
            Player p = new PlayerBuilder()
                    .setPosition(Position.FORWARD)
                    .setAttributes(new PlayerAttributes(90, 20, 50, 90, 90, 90, 99))
                    .build();
            team.addPlayer(p);
        }
        for (int i = 0; i < 6; i++) {
            Player p = new PlayerBuilder()
                    .setPosition(Position.MIDFIELDER)
                    .setAttributes(new PlayerAttributes(50, 20, 90, 90, 90, 90, 99))
                    .build();
            team.addPlayer(p);
        }
        for (int i = 0; i < 5; i++) {
            Player p = new PlayerBuilder()
                    .setPosition(Position.DEFENDER)
                    .setAttributes(new PlayerAttributes(20, 90, 50, 90, 50, 90, 99))
                    .build();
            team.addPlayer(p);
        }

        assertEquals(83, (int) team.calculateTeamAttackRating());
        assertEquals(77, (int) team.calculateTeamMidfieldRating());
        assertEquals(79, (int) team.calculateTeamDefenseRating());

        assertEquals(78, (int) team.calculateTeamRating());
    }
}