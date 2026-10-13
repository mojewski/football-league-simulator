package com.github.mojewski.footballleaguesimulator.service.league;

import com.github.mojewski.footballleaguesimulator.model.league.League;
import com.github.mojewski.footballleaguesimulator.model.league.schedule.Matchday;
import com.github.mojewski.footballleaguesimulator.model.match.Match;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.utils.Calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleGenerator {

    private final Calendar calendar;

    public ScheduleGenerator(Calendar calendar) {
        this.calendar = calendar;
    }

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

        LocalDate currentMatchFriday = calendar.getFirstMatchdayDate();

        int roundsCount = teams.size() - 1;
        for (int i = 0; i < roundsCount; i++) {
            matchdayCounter++;
            List<Match> matches = generateMatches(homeTeams, awayTeams, currentMatchFriday);
            schedule.add(new Matchday(matchdayCounter, matches));
            rotateTeams(homeTeams, awayTeams);

            currentMatchFriday = calendar.getNextValidMatchFriday(currentMatchFriday);
        }

        int firstRoundDays = schedule.size();
        for (int i = 0; i < firstRoundDays; i++) {
            matchdayCounter++;
            Matchday originalMatchday = schedule.get(i);

            LocalDate rematchFriday = currentMatchFriday;

            List<Match> rematchMatches = new ArrayList<>();
            List<Match> originalMatches = originalMatchday.matches();

            for (int j = 0; j < originalMatches.size(); j++) {
                Match m = originalMatches.get(j);
                Match rematch = new Match(m.getAwayTeam(), m.getHomeTeam());

                int dayOffset = j % 3;
                rematch.setMatchDate(rematchFriday.plusDays(dayOffset));
                rematchMatches.add(rematch);
            }

            schedule.add(new Matchday(matchdayCounter, rematchMatches));

            currentMatchFriday = calendar.getNextValidMatchFriday(currentMatchFriday);
        }

        return schedule;
    }

    private List<Match> generateMatches(List<Team> homeTeams, List<Team> awayTeams, LocalDate baseFriday) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < homeTeams.size(); i++) {
            Team home = homeTeams.get(i);
            Team away = awayTeams.get(i);

            if (home.getName().equals("pause") || away.getName().equals("pause")) {
                continue;
            }

            Match match = new Match(home, away);

            int dayOffset = matches.size() % 3;
            match.setMatchDate(baseFriday.plusDays(dayOffset));
            matches.add(match);
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