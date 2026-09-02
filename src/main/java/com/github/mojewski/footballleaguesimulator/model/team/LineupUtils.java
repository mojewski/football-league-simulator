package com.github.mojewski.footballleaguesimulator.model.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LineupUtils {

    public static List<Player> getBestForPosition(List<Player> pool, Position position, int count, Comparator<Player> comparator) {
        return pool.stream()
                .filter(player -> player.getPosition() == position)
                .sorted(comparator)
                .limit(count)
                .toList();
    }

    public static List<Player> buildLineupForFormation(List<Player> pool, Formation formation, Comparator<Player> comparator) {
        List<Player> lineup = new ArrayList<>();
        lineup.addAll(getBestForPosition(pool, Position.GOALKEEPER, formation.getGoalkeeper(), comparator));
        lineup.addAll(getBestForPosition(pool, Position.DEFENDER, formation.getDefenders(), comparator));
        lineup.addAll(getBestForPosition(pool, Position.MIDFIELDER, formation.getMidfielders(), comparator));
        lineup.addAll(getBestForPosition(pool, Position.FORWARD, formation.getForwards(), comparator));
        return lineup;
    }
}