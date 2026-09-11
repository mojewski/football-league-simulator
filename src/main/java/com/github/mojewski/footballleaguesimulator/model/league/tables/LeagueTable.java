package com.github.mojewski.footballleaguesimulator.model.league.tables;

import com.github.mojewski.footballleaguesimulator.model.league.LeagueRules;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeagueTable {

    private final List<LeagueTableRow> rows = new ArrayList<>();

    public void addTeam(Team team) {
        if (team != null && rows.stream().noneMatch(row -> row.getTeam().equals(team))) {
            rows.add(new LeagueTableRow(team));
        }
    }

    public void removeTeam(Team team) {
        rows.removeIf(row -> row.getTeam().equals(team));
    }

    public void updateTable(LeagueRules rules) {
        Comparator<LeagueTableRow> comparator = Comparator
                .comparingInt(LeagueTableRow::getPoints)
                .thenComparingInt(LeagueTableRow::getGoalDifference)
                .thenComparingInt(LeagueTableRow::getGoalsScored)
                .reversed();

        rows.sort(comparator);
        for (int i = 0; i < rows.size(); i++) {
            rows.get(i).setPosition(i + 1);
        }
    }

    public Team getTeamAtPosition(int position) {
        if (position < 1 || position > rows.size()) {
            return null;
        }
        return rows.get(position - 1).getTeam();
    }

    public List<Team> getTeamsInStatusZone(int startPosition, int count) {
        if (startPosition < 1 || count <= 0 || rows.isEmpty()) {
            return new ArrayList<>();
        }

        int fromIndex = Math.min(startPosition - 1, rows.size());
        int toIndex = Math.min(fromIndex + count, rows.size());

        return rows.subList(fromIndex, toIndex).stream()
                .map(LeagueTableRow::getTeam)
                .toList();
    }

    public List<LeagueTableRow> getRows() {
        return rows;
    }
}