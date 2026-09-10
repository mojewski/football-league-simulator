package com.github.mojewski.footballleaguesimulator.model.league;

import com.github.mojewski.footballleaguesimulator.model.Country;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League {

    private Long id;
    private String name;
    private int reputation;
    private int tier;
    private Country country;
    private LeagueCalendar calendar;
    private LeagueLeaderboards leaderboards;
    private LeagueRules rules;
    private LeagueTable table;

    private final List<Team> teams = new ArrayList<>();

    public League(String name, int reputation, int tier, Country country,
                  LeagueCalendar calendar, LeagueLeaderboards leaderboards, LeagueRules rules) {
        this.name = name;
        this.reputation = reputation;
        this.tier = tier;
        this.country = country;
        startNewSeason(calendar, leaderboards, rules);
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            teams.add(team);
            team.setLeague(this);
            if (this.table != null) {
                this.table.addTeam(team);
            }
        }
    }

    public void removeTeam(Team team) {
        if (team != null && teams.contains(team)) {
            teams.remove(team);
            team.setLeague(null);
            if (this.table != null) {
                this.table.removeTeam(team);
            }
        }
    }

    public void startNewSeason(LeagueCalendar calendar, LeagueLeaderboards leaderboards, LeagueRules rules) {
        this.calendar = calendar;
        this.leaderboards = leaderboards;
        this.rules = rules;
        this.table = new LeagueTable();

        for (Team team : teams) {
            this.table.addTeam(team);
        }
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
    public LeagueCalendar getCalendar() { return calendar; }
    public LeagueLeaderboards getLeaderboards() { return leaderboards; }
    public LeagueRules getRules() { return rules; }
    public LeagueTable getTable() { return table; }
}