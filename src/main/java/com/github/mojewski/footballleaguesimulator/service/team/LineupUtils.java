package com.github.mojewski.footballleaguesimulator.service.team;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Formation;

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
        List<Player> availablePool = new ArrayList<>(pool);

        fillPosition(lineup, availablePool, Position.GOALKEEPER, formation.getGoalkeeper(), comparator);
        fillPosition(lineup, availablePool, Position.DEFENDER, formation.getDefenders(), comparator);
        fillPosition(lineup, availablePool, Position.MIDFIELDER, formation.getMidfielders(), comparator);
        fillPosition(lineup, availablePool, Position.FORWARD, formation.getForwards(), comparator);

        int targetSize = formation.getGoalkeeper() + formation.getDefenders() + formation.getMidfielders() + formation.getForwards();

        if (lineup.size() < targetSize && !availablePool.isEmpty()) {
            List<Player> fallbackFill = availablePool.stream()
                    .sorted(comparator)
                    .limit(targetSize - lineup.size())
                    .toList();

            lineup.addAll(fallbackFill);
        }

        return lineup;
    }

    private static void fillPosition(List<Player> lineup, List<Player> availablePool, Position position, int count, Comparator<Player> comparator) {
        List<Player> selected = getBestForPosition(availablePool, position, count, comparator);
        lineup.addAll(selected);
        availablePool.removeAll(selected);
    }
}