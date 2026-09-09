package com.github.mojewski.footballleaguesimulator.service;

import com.github.mojewski.footballleaguesimulator.TODO.Calendar;
import com.github.mojewski.footballleaguesimulator.TODO.Simulation;
import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.player.FreeAgents;
import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.player.PlayerContract;
import com.github.mojewski.footballleaguesimulator.model.player.Position;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.List;
import java.util.Optional;

public class TransferService {

    private final RandomNumberGenerator random;
    private final SalaryCalculator salaryCalculator;
    private final Simulation simulation;
    private final FreeAgents freeAgents;

    private static final int TARGET_SQUAD_SIZE = 25;

    public TransferService(RandomNumberGenerator random, SalaryCalculator salaryCalculator, Simulation simulation, FreeAgents freeAgents) {
        this.random = random;
        this.salaryCalculator = salaryCalculator;
        this.simulation = simulation;
        this.freeAgents = freeAgents;
    }

    public boolean isClubAbleToTransfer(Calendar calendar, Team team) {
        return calendar.isTransferWindowActive && team.getPlayerList().size() < TARGET_SQUAD_SIZE;
    }

    public boolean doTransfer(Team team, Calendar calendar, Position position) {
        if (!isClubAbleToTransfer(calendar, team)) {
            return false;
        }

        int randomOption = random.getRandomInt(0, 1);
        Optional<Player> optionalPlayer = pickAffordablePlayer(team, position, randomOption);

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            Team sellingTeam = player.getTeam();

            int randomDuration = random.getRandomInt(1, 4);
            double yearlySalary = salaryCalculator.calculateExpectedMonthlySalary(player) * 12;
            double transferFee = calculateTransferFee(player);

            team.subtractBudget(yearlySalary + transferFee);
            if (sellingTeam != null) {
                sellingTeam.addBudget(transferFee);
            }

            PlayerContract newContract = new PlayerContract(yearlySalary, randomDuration);
            player.signContract(newContract, team);

            freeAgents.removeFreeAgent(player);
            return true;
        }

        return false;
    }

    public Optional<League> getRandomLeague(Team buyingTeam) {
        List<League> leagues = simulation.getLeagues().stream()
                .filter(league -> Math.abs(league.getReputation() - buyingTeam.getReputation()) <= 5)
                .toList();

        if (leagues.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = random.getRandomInt(0, leagues.size() - 1);
        return Optional.of(leagues.get(randomIndex));
    }

    public Optional<Team> getRandomTeam(Team buyingTeam) {
        Optional<League> optionalLeague = getRandomLeague(buyingTeam);

        if (optionalLeague.isEmpty()) {
            return Optional.empty();
        }

        League league = optionalLeague.get();

        List<Team> teams = league.getTeams().stream()
                .filter(team -> !team.equals(buyingTeam))
                .filter(team -> Math.abs(team.getReputation() - buyingTeam.getReputation()) <= 10)
                .toList();

        if (teams.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = random.getRandomInt(0, teams.size() - 1);
        return Optional.of(teams.get(randomIndex));
    }

    public List<Player> getAffordablePlayers(Team buyingTeam, Position position, int option) {
        List<Player> players;

        if (option == 0) {
            Optional<Team> sellerTeam = getRandomTeam(buyingTeam);
            if (sellerTeam.isEmpty()) {
                return List.of();
            }
            players = sellerTeam.get().getPlayerList();
        } else {
            players = freeAgents.getFreeAgents();
        }

        return players.stream()
                .filter(player -> Math.abs(player.getOverall() - buyingTeam.getReputation()) <= 5)
                .filter(player -> player.getPosition() == position)
                .filter(player -> {
                    double yearlySalary = salaryCalculator.calculateExpectedMonthlySalary(player) * 12;
                    double transferFee = calculateTransferFee(player);
                    return buyingTeam.getBudget() >= (yearlySalary + transferFee);
                })
                .toList();
    }

    public Optional<Player> pickAffordablePlayer(Team team, Position position, int option) {
        List<Player> players = getAffordablePlayers(team, position, option);

        if (players.isEmpty()) {
            return Optional.empty();
        }

        int randomIndex = random.getRandomInt(0, players.size() - 1);
        return Optional.of(players.get(randomIndex));
    }

    private double calculateTransferFee(Player player) {
        if (player.getTeam() == null) {
            return 0.0;
        }
        double yearlySalary = salaryCalculator.calculateExpectedMonthlySalary(player) * 12;
        return yearlySalary * 3.0;
    }
}