package com.github.mojewski.footballleaguesimulator.model.league;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.league.schedule.LeagueSchedule;
import com.github.mojewski.footballleaguesimulator.model.league.tables.LeagueLeaderboards;
import com.github.mojewski.footballleaguesimulator.model.league.tables.LeagueTable;
import com.github.mojewski.footballleaguesimulator.model.team.Team;
import com.github.mojewski.footballleaguesimulator.service.league.ScheduleGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League {

    private Long id;
    private String name;
    private int reputation;
    private int tier;
    private Country country;

    private LeagueSchedule schedule;
    private LeagueLeaderboards leaderboards;
    private LeagueRules rules;
    private LeagueTable table = new LeagueTable();

    private final List<Team> teams = new ArrayList<>();

    public League(String name) {
        this.name = name;
    }

    public League(String name, int reputation, int tier, Country country) {
        this.name = name;
        this.reputation = reputation;
        this.tier = tier;
        this.country = country;
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            teams.add(team);
            team.setLeague(this);
            this.table.addTeam(team);
        }
    }

    public void removeTeam(Team team) {
        if (team != null && teams.contains(team)) {
            teams.remove(team);
            team.setLeague(null);
            this.table.removeTeam(team);
        }
    }

    public void startNewSeason(LeagueLeaderboards leaderboards, LeagueRules rules, ScheduleGenerator scheduleGenerator) {
        this.leaderboards = leaderboards;
        this.rules = rules;
        this.table = new LeagueTable();

        for (Team team : teams) {
            this.table.addTeam(team);
        }

        generateSchedule(scheduleGenerator);
    }

    public void generateSchedule(ScheduleGenerator scheduleGenerator) {
        this.schedule = new LeagueSchedule(scheduleGenerator.generateFullSchedule(this));
    }

    public Team getChampion() {
        return table.getTeamAtPosition(1);
    }

    public List<Team> getPromotedTeams() {
        if (tier <= 1 || rules == null) {
            return Collections.emptyList();
        }
        int count = rules.getPromotionSpots();
        return table.getTeamsInStatusZone(1, count);
    }

    public List<Team> getRelegatedTeams() {
        if (rules == null || table.getRows().isEmpty()) {
            return Collections.emptyList();
        }
        int count = rules.getRelegationSpots();
        int totalTeams = table.getRows().size();
        int startPosition = totalTeams - count + 1;
        return table.getTeamsInStatusZone(startPosition, count);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getReputation() { return reputation; }
    public int getTier() { return tier; }
    public Country getCountry() { return country; }
    public List<Team> getTeams() { return teams; }
    public LeagueSchedule getSchedule() { return schedule; }
    public LeagueLeaderboards getLeaderboards() { return leaderboards; }
    public LeagueRules getRules() { return rules; }
    public LeagueTable getTable() { return table; }
}