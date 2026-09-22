package com.github.mojewski.footballleaguesimulator.model.match;

import com.github.mojewski.footballleaguesimulator.model.player.Player;
import com.github.mojewski.footballleaguesimulator.model.team.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchStats {
    private final int homeGoals;
    private final int awayGoals;

    private final List<MatchEvent> events;

    private double homeXG;
    private double awayXG;

    private int homePossession;
    private int awayPossession;

    private int homeShoots;
    private int awayShoots;

    private int homeShootsOnTarget;
    private int awayShootsOnTarget;

    private int homeCorners;
    private int awayCorners;

    private int homeAccuratePasses;
    private int awayAccuratePasses;

    private int homeTotalPasses;
    private int awayTotalPasses;

    private int homeYellowCards;
    private int awayYellowCards;

    private int homeRedCards;
    private int awayRedCards;

    private int homeGoalkeeperSaves;
    private int awayGoalkeeperSaves;

    private int stoppageTime;

    public MatchStats(MatchOutcome outcome, List<MatchEvent> events) {
        this.homeGoals = outcome.getHomeGoals();
        this.awayGoals = outcome.getAwayGoals();

        this.events = events != null ? events : new ArrayList<>();

        this.homeXG = outcome.getHomeXG();
        this.awayXG = outcome.getAwayXG();
    }

    public void setHomePossession(int homePossession) {
        this.homePossession = homePossession;
    }

    public void setAwayPossession(int awayPossession) {
        this.awayPossession = awayPossession;
    }

    public void setHomeShoots(int homeShoots) {
        this.homeShoots = homeShoots;
    }

    public void setAwayShoots(int awayShoots) {
        this.awayShoots = awayShoots;
    }

    public void setHomeShootsOnTarget(int homeShootsOnTarget) {
        this.homeShootsOnTarget = homeShootsOnTarget;
    }

    public void setAwayShootsOnTarget(int awayShootsOnTarget) {
        this.awayShootsOnTarget = awayShootsOnTarget;
    }

    public void setHomeCorners(int homeCorners) {
        this.homeCorners = homeCorners;
    }

    public void setAwayCorners(int awayCorners) {
        this.awayCorners = awayCorners;
    }

    public void setHomeAccuratePasses(int homeAccuratePasses) {
        this.homeAccuratePasses = homeAccuratePasses;
    }

    public void setAwayAccuratePasses(int awayAccuratePasses) {
        this.awayAccuratePasses = awayAccuratePasses;
    }

    public void setHomeTotalPasses(int homeTotalPasses) {
        this.homeTotalPasses = homeTotalPasses;
    }

    public void setAwayTotalPasses(int awayTotalPasses) {
        this.awayTotalPasses = awayTotalPasses;
    }

    public void setHomeYellowCards(int homeYellowCards) {
        this.homeYellowCards = homeYellowCards;
    }

    public void setAwayYellowCards(int awayYellowCards) {
        this.awayYellowCards = awayYellowCards;
    }

    public void setHomeRedCards(int homeRedCards) {
        this.homeRedCards = homeRedCards;
    }

    public void setAwayRedCards(int awayRedCards) {
        this.awayRedCards = awayRedCards;
    }

    public void setHomeGoalkeeperSaves(int homeGoalkeeperSaves) {
        this.homeGoalkeeperSaves = homeGoalkeeperSaves;
    }

    public void setAwayGoalkeeperSaves(int awayGoalkeeperSaves) {
        this.awayGoalkeeperSaves = awayGoalkeeperSaves;
    }

    public void setStoppageTime(int stoppageTime) {
        this.stoppageTime = stoppageTime;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public List<MatchEvent> getEvents() {
        return events;
    }

    public double getHomeXG() {
        return homeXG;
    }

    public double getAwayXG() {
        return awayXG;
    }

    public int getHomePossession() {
        return homePossession;
    }

    public int getAwayPossession() {
        return awayPossession;
    }

    public int getHomeShoots() {
        return homeShoots;
    }

    public int getAwayShoots() {
        return awayShoots;
    }

    public int getHomeShootsOnTarget() {
        return homeShootsOnTarget;
    }

    public int getAwayShootsOnTarget() {
        return awayShootsOnTarget;
    }

    public int getHomeCorners() {
        return homeCorners;
    }

    public int getAwayCorners() {
        return awayCorners;
    }

    public int getHomeAccuratePasses() {
        return homeAccuratePasses;
    }

    public int getAwayAccuratePasses() {
        return awayAccuratePasses;
    }

    public int getHomeTotalPasses() {
        return homeTotalPasses;
    }

    public int getAwayTotalPasses() {
        return awayTotalPasses;
    }

    public int getHomeYellowCards() {
        return homeYellowCards;
    }

    public int getAwayYellowCards() {
        return awayYellowCards;
    }

    public int getHomeRedCards() {
        return homeRedCards;
    }

    public int getAwayRedCards() {
        return awayRedCards;
    }

    public int getHomeGoalkeeperSaves() {
        return homeGoalkeeperSaves;
    }

    public int getAwayGoalkeeperSaves() {
        return awayGoalkeeperSaves;
    }

    public int getStoppageTime() {
        return stoppageTime;
    }
}