package com.github.mojewski.footballleaguesimulator.service.match.event;

import com.github.mojewski.footballleaguesimulator.model.match.EventType;
import com.github.mojewski.footballleaguesimulator.model.match.MatchEvent;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.utils.RandomNumberGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

public class MatchEventGenerator {

    private final RandomNumberGenerator random;

    public MatchEventGenerator(RandomNumberGenerator random) {
        this.random = random;
    }

    public int generateStoppageTime() {
        return random.getRandomInt(1, 11);
    }

    public MatchEvent generateGoalEvent(Team team, boolean isHomeTeam, BiFunction<Integer, Team, List<Player>> activePlayersProvider) {
        int minute = random.getRandomInt(1, (90 + generateStoppageTime()));

        List<Player> activePlayers = activePlayersProvider.apply(minute, team);

        Player scorer = selectPlayerForGoal(activePlayers);
        Player assistPlayer = selectAssistPlayer(activePlayers, scorer);

        return new MatchEvent(minute, EventType.GOAL, team, scorer, assistPlayer, isHomeTeam);
    }

    private Player selectPlayerForGoal(List<Player> availablePlayers) {
        return WeightedRandomSelector.selectWeighted(
                availablePlayers,
                this::calculateGoalWeight,
                random
        );
    }

    private double calculateGoalWeight(Player player) {
        PositionWeight weightConfig = getPositionWeight(player);
        double positionFactor = weightConfig.getGoalMultiplier();
        double overallFactor = Math.max(1, player.getEffectiveOverall() - 50);

        return positionFactor * overallFactor;
    }

    private Player selectAssistPlayer(List<Player> availablePlayers, Player scorer) {
        boolean hasAssist = random.getRandomInt(1, 100) <= 60;
        if (!hasAssist || availablePlayers.size() <= 1) {
            return null;
        }

        List<Player> potentialAssistants = availablePlayers.stream()
                .filter(player -> !player.equals(scorer))
                .toList();

        return WeightedRandomSelector.selectWeighted(
                potentialAssistants,
                this::calculateAssistWeight,
                random
        );
    }

    private double calculateAssistWeight(Player player) {
        PositionWeight weightConfig = getPositionWeight(player);
        double positionFactor = weightConfig.getAssistMultiplier();
        double overallFactor = Math.max(1, player.getEffectiveOverall() - 50);

        return positionFactor * overallFactor;
    }

    public MatchEvent generateCardEvent(Team team, EventType cardType, boolean isHomeTeam, BiFunction<Integer, Team, List<Player>> activePlayersProvider) {
        int minute = random.getRandomInt(1, (90 + generateStoppageTime()));
        List<Player> activePlayers = activePlayersProvider.apply(minute, team);

        Player penalizedPlayer = WeightedRandomSelector.selectWeighted(
                activePlayers,
                player -> calculateCardWeight(player, cardType),
                random
        );

        return new MatchEvent(minute, cardType, team, penalizedPlayer, null, isHomeTeam);
    }

    private double calculateCardWeight(Player player, EventType cardType) {
        PositionWeight weightConfig = getPositionWeight(player);

        double positionFactor;

        if (cardType == EventType.RED_CARD) {
            positionFactor = weightConfig.getRedCardMultiplier();
        } else {
            positionFactor = weightConfig.getYellowCardMultiplier();
        }

        return positionFactor;
    }

    public MatchEvent generateInjuryEvent(Team team, boolean isHomeTeam, BiFunction<Integer, Team, List<Player>> activePlayersProvider) {
        int minute = random.getRandomInt(1, (90 + generateStoppageTime()));
        List<Player> activePlayers = activePlayersProvider.apply(minute, team);

        Player injuredPlayer = WeightedRandomSelector.selectWeighted(
                activePlayers,
                this::calculateInjuryWeight,
                random
        );

        return new MatchEvent(minute, EventType.INJURY, team, injuredPlayer, null, isHomeTeam);
    }

    private double calculateInjuryWeight(Player player) {
        PositionWeight weightConfig = getPositionWeight(player);
        double positionFactor = weightConfig.getInjuryMultiplier();

        double ageFactor;
        if (player.getAge() > 30) {
            ageFactor = 1.3;
        } else {
            ageFactor = 1.0;
        }

        return positionFactor * ageFactor;
    }

    public MatchEvent generateSubstitutionEvent(Team team, boolean isHomeTeam, BiFunction<Integer, Team, List<Player>> activePlayersProvider, List<Player> bench) {
        if (bench.isEmpty()) {
            return null;
        }

        int minute = random.getRandomInt(45, (90 + generateStoppageTime()));
        List<Player> activePlayers = activePlayersProvider.apply(minute, team);

        List<Player> outfieldPlayers = activePlayers.stream()
                .filter(player -> player.getPosition() != Position.GOALKEEPER)
                .toList();

        if (outfieldPlayers.isEmpty()) {
            return null;
        }

        Player playerOut = outfieldPlayers.stream()
                .min(Comparator.comparingInt(Player::getStamina))
                .orElse(outfieldPlayers.getFirst());

        Player playerIn = selectBenchPlayerForSubstitution(bench, playerOut);

        return new MatchEvent(minute, EventType.SUBSTITUTION, team, playerOut, playerIn, isHomeTeam);
    }

    private Player selectBenchPlayerForSubstitution(List<Player> bench, Player playerOut) {
        List<Player> samePositionBench = bench.stream()
                .filter(p -> p.getPosition() == playerOut.getPosition())
                .toList();

        if (!samePositionBench.isEmpty()) {
            return samePositionBench.stream()
                    .max(Comparator.comparingInt(Player::getEffectiveOverall))
                    .orElse(samePositionBench.getFirst());
        }

        return bench.stream()
                .max(Comparator.comparingInt(Player::getEffectiveOverall))
                .orElse(bench.getFirst());
    }

    private PositionWeight getPositionWeight(Player player) {
        return switch (player.getPosition()) {
            case FORWARD -> PositionWeight.FORWARD;
            case MIDFIELDER -> PositionWeight.MIDFIELDER;
            case DEFENDER -> PositionWeight.DEFENDER;
            case GOALKEEPER -> PositionWeight.GOALKEEPER;
        };
    }
}