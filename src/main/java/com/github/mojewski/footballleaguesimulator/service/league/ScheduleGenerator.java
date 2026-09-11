package com.github.mojewski.footballleaguesimulator.service.league;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.league.schedule.Matchday;
import com.github.mojewski.footballleaguesimulator.model.match.Match;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {

    private List<Team> teams;

    private int numberOfMatchday;

    private List<Team> homeTeams;
    private List<Team> awayTeams;

    private List<Matchday> schedule = new ArrayList<>();

    public ScheduleGenerator(League league) {
        this.teams = new ArrayList<>(league.getTeams());
        if (this.teams.size() % 2 != 0) {
            this.teams.add(new Team("pause"));
        }
    }

    public List<Matchday> generateFullSchedule() {
        schedule.clear();
        numberOfMatchday = 0;

        divideTeams();
        generateFirstRound();
        generateSecondRound();

        return new ArrayList<>(schedule);
    }

    private void divideTeams() {
        int halfLeague = teams.size() / 2;
        this.homeTeams = new ArrayList<>(teams.subList(0, halfLeague));
        this.awayTeams = new ArrayList<>(teams.subList(halfLeague, teams.size()));
    }

    private List<Match> generateMatches() {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < homeTeams.size(); i++) {
            Team home = homeTeams.get(i);
            Team away = awayTeams.get(i);

            if (home.getName().equals("pause") || away.getName().equals("pause")) {
                continue;
            }
            matches.add(new Match(home, away));
        }

        rotateTeams();
        return matches;
    }

    private void rotateTeams() {
        Team movedFromHome = homeTeams.removeLast();
        awayTeams.add(movedFromHome);

        Team movedFromAway = awayTeams.removeFirst();
        homeTeams.add(1, movedFromAway);
    }

    private Matchday setMatchday() {
        this.numberOfMatchday++;
        return new Matchday(numberOfMatchday, generateMatches());
    }

    private void generateFirstRound() {
        for (int i = 0; i < teams.size() - 1; i++) {
            this.schedule.add(setMatchday());
        }
    }

    private void generateSecondRound() {
        int firstHalfRoundsCount = schedule.size();

        for (int i = 0; i < firstHalfRoundsCount; i++) {
            Matchday originalMatchday = schedule.get(i);

            List<Match> rematchMatches = originalMatchday.matches().stream()
                    .map(m -> new Match(m.getAwayTeam(), m.getHomeTeam()))
                    .toList();

            this.numberOfMatchday++;
            this.schedule.add(new Matchday(this.numberOfMatchday, rematchMatches));
        }
    }
}
