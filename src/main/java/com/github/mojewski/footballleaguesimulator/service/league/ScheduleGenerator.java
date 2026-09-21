package com.github.mojewski.footballleaguesimulator.service.league;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.league.schedule.Matchday;
import com.github.mojewski.footballleaguesimulator.model.match.Match;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {

    public List<Matchday> generateFullSchedule(League league) {
        List<Team> teams = new ArrayList<>(league.getTeams());
        if (teams.size() % 2 != 0) {
            teams.add(new Team("pause"));
        }

        int halfLeague = teams.size() / 2;
        List<Team> homeTeams = new ArrayList<>(teams.subList(0, halfLeague));
        List<Team> awayTeams = new ArrayList<>(teams.subList(halfLeague, teams.size()));

        List<Matchday> schedule = new ArrayList<>();
        int matchdayCounter = 0;

        int roundsCount = teams.size() - 1;
        for (int i = 0; i < roundsCount; i++) {
            matchdayCounter++;
            List<Match> matches = generateMatches(homeTeams, awayTeams);
            schedule.add(new Matchday(matchdayCounter, matches));
            rotateTeams(homeTeams, awayTeams);
        }

        int firstRoundDays = schedule.size();
        for (int i = 0; i < firstRoundDays; i++) {
            Matchday originalMatchday = schedule.get(i);

            List<Match> rematchMatches = originalMatchday.matches().stream()
                    .map(m -> new Match(m.getAwayTeam(), m.getHomeTeam()))
                    .toList();

            matchdayCounter++;
            schedule.add(new Matchday(matchdayCounter, rematchMatches));
        }

        return schedule;
    }

    private List<Match> generateMatches(List<Team> homeTeams, List<Team> awayTeams) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < homeTeams.size(); i++) {
            Team home = homeTeams.get(i);
            Team away = awayTeams.get(i);

            if (home.getName().equals("pause") || away.getName().equals("pause")) {
                continue;
            }
            matches.add(new Match(home, away));
        }

        return matches;
    }

    private void rotateTeams(List<Team> homeTeams, List<Team> awayTeams) {
        Team movedFromHome = homeTeams.removeLast();
        awayTeams.add(movedFromHome);

        Team movedFromAway = awayTeams.removeFirst();
        homeTeams.add(1, movedFromAway);
    }
}