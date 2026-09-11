package com.github.mojewski.footballleaguesimulator.model.league.schedule;

import com.github.mojewski.footballleaguesimulator.model.match.Match;

import java.util.List;

public record Matchday(int number, List<Match> matches) {
}
